package com.example.demo.controller;

import com.example.demo.dto.StatisticsDTO;
import com.example.demo.entity.Bill;
import com.example.demo.service.BillService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("statistics")
public class StatisticsController {
    @Resource
    private BillService billService;

    // 统计页面
    @GetMapping("/index")
    public String index(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {
        
        int currentYear = (year != null) ? year : LocalDate.now().getYear();
        Integer currentMonth = month;
        
        // 月度趋势数据
        List<StatisticsDTO> monthlyStats = billService.getMonthlyStats(currentYear);
        // 分类占比数据
        List<StatisticsDTO> categoryStats = billService.getCategoryStats(
            year, month
        );
        // 年度统计
        List<StatisticsDTO> yearlyStats = billService.getYearlyStats();
        
        model.addAttribute("monthlyStats", monthlyStats);
        model.addAttribute("categoryStats", categoryStats);
        model.addAttribute("yearlyStats", yearlyStats);
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("currentMonth", currentMonth);
        
        return "statistics/index";
    }

    // API: 获取月度统计数据
    @GetMapping("/api/monthly")
    @ResponseBody
    public List<StatisticsDTO> getMonthlyStats(@RequestParam int year) {
        return billService.getMonthlyStats(year);
    }

    // API: 获取分类统计数据
    @GetMapping("/api/category")
    @ResponseBody
    public List<StatisticsDTO> getCategoryStats(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return billService.getCategoryStats(year, month);
    }

    // 导出Excel
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Bill> bills = billService.getAll();
        
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("账单数据");
        
        // 创建标题样式
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        
        // 创建标题行
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "标题", "日期", "金额", "分类", "说明"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // 填充数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int rowNum = 1;
        for (Bill bill : bills) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(bill.getId());
            row.createCell(1).setCellValue(bill.getTitle());
            row.createCell(2).setCellValue(bill.getBillTime() != null ? sdf.format(bill.getBillTime()) : "");
            row.createCell(3).setCellValue(bill.getPrice());
            row.createCell(4).setCellValue(bill.getName() != null ? bill.getName() : "");
            row.createCell(5).setCellValue(bill.getExplains() != null ? bill.getExplains() : "");
        }
        
        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=bills.xlsx");
        
        // 写入响应
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
