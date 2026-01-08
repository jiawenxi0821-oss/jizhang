-- 检查数据库表结构
USE billdb;

-- 检查现有表
SHOW TABLES;

-- 检查用户表结构
DESCRIBE user;

-- 检查账单表结构  
DESCRIBE bill;

-- 检查分类表结构
DESCRIBE bill_type;

-- 检查预算表是否存在
SHOW TABLES LIKE 'budget';

-- 如果预算表不存在，创建它
CREATE TABLE IF NOT EXISTS budget (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    year INT NOT NULL COMMENT '年份',
    month INT DEFAULT NULL COMMENT '月份（NULL表示年度预算）',
    type_id INT DEFAULT NULL COMMENT '分类ID（NULL表示总预算）',
    amount DECIMAL(10,2) NOT NULL COMMENT '预算金额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_year_month (year, month),
    INDEX idx_type_id (type_id)
) COMMENT='预算表';

-- 检查用户表是否有新字段，如果没有则添加
ALTER TABLE user 
ADD COLUMN IF NOT EXISTS phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
ADD COLUMN IF NOT EXISTS real_name VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
ADD COLUMN IF NOT EXISTS update_time DATETIME DEFAULT NULL COMMENT '更新时间';

-- 检查账单表是否有用户关联字段
-- ALTER TABLE bill ADD COLUMN IF NOT EXISTS user_id INT DEFAULT NULL COMMENT '用户ID';

-- 查看最终表结构
DESCRIBE user;
DESCRIBE budget;