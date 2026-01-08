package com.example.demo.controller;
import com.example.demo.entity.Bill;
import com.example.demo.entity.BillType;
import com.example.demo.service.BillService;
import com.example.demo.service.BillTypeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
@RequestMapping("bill")
@Controller
public class BillController {
    @Resource
    private BillService billService;
    @Resource
    private BillTypeService billTypeService;
    @RequestMapping("/list-page")
    public String  listPage(
            Bill bill,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Model model)
    {
        System.out.println("@@@"+bill.getTypeId()+"$$$");
        //具有分页的bill数据
       PageInfo<Bill> pageInfo = billService.search(bill,pageNum,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        //类别数据
        List<BillType> billTypeList = billTypeService.search();
        model.addAttribute("billTypeList",billTypeList);
//        回传查询条件
        model.addAttribute("bill", bill);
        return "bill/list-page";
    }

    //进入新增页面
    @RequestMapping("/toAdd")
    public  String toAdd(Model model){
        //类别数据
        List<BillType> billTypeList = billTypeService.search();
        model.addAttribute("billTypeList",billTypeList);

        return "bill/add";
    }
    //新增数据
    @RequestMapping("/add")
    public String add(Bill bill){
       billService.insert(bill);

        return "redirect:/bill/list-page";// 重定向
    }


    //根据ID，删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        billService.removeById(id);
        return "redirect:/bill/list-page";
    }
//问号传值
    @RequestMapping("/delete2")
    public String delete2(String id ){
        System.out.println(id);
        billService.removeById(id);
        return "redirect:/bill/list-page";
    }


    //批量删除
    @RequestMapping("/batchDel")
    public String batchDel(String[] ids){
        if(ids != null && ids.length > 0) {
            billService.batchDelete(ids);
        }
        return  "redirect:/bill/list-page";
    }

    //进入编辑页面
    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") String id, Model model) {
        // 查询账单数据
        Bill bill = billService.getById(id);
        model.addAttribute("bill", bill);
        // 类别数据
        List<BillType> billTypeList = billTypeService.search();
        model.addAttribute("billTypeList", billTypeList);
        return "bill/update";
    }

    // 更新账单
    @RequestMapping("/update")
    public String update(Bill bill) {
        billService.update(bill);
        return "redirect:/bill/list-page";
    }


}
