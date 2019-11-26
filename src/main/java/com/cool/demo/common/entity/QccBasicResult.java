package com.cool.demo.common.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by vincent on 2019-11-17
 */
public class QccBasicResult implements Serializable {

    private static final long serialVersionUID = -2380148777392140016L;

    public static void main(String[] args) {
        String basic = "{\"OrderNumber\":\"ECI2019111715481384655594\",\"Result\":{\"KeyNo\":\"5681857e97f388bce24a85a524f85a51\",\"Name\":\"杭州递递叭叭新能源汽车有限公司\",\"No\":\"330184000502846\",\"BelongOrg\":\"杭州市余杭区市场监督管理局\",\"OperName\":\"肖友康\",\"StartDate\":\"2016-07-28 00:00:00\",\"EndDate\":\"\",\"Status\":\"存续\",\"Province\":\"ZJ\",\"UpdatedDate\":\"2019-11-12 14:20:30\",\"CreditCode\":\"91330110MA27YAMJ54\",\"RegistCapi\":\"2000万元人民币\",\"EconKind\":\"有限责任公司(自然人投资或控股)\",\"Address\":\"浙江省杭州市余杭区南苑街道人民大道750#二楼\",\"Scope\":\"销售:汽车、汽车配件、汽车装饰品、日用品、智能设备;食品经营;服务:电动汽车的充电,汽车租赁,汽车事务咨询,汽车上牌、年检代理,网络预约出租汽车客运,新能源汽车充电站点的建设,车辆充、换电站的运营和维护,临时停车服务;充电场站的开发建设;计算机系统集成、网络工程、通信技术、自动化控制系统的技术开发、计算机技术服务、计算机技术咨询、计算机软硬件开发。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"TermStart\":\"2016-07-28 00:00:00\",\"TeamEnd\":\"9999-09-09 00:00:00\",\"CheckDate\":\"2019-07-01 00:00:00\",\"OrgNo\":\"MA27YAMJ-5\",\"IsOnStock\":\"0\",\"StockNumber\":null,\"StockType\":null,\"OriginalName\":[{\"Name\":\"杭州递递叭叭新能源汽车租赁有限公司\",\"ChangeDate\":\"2017-04-26\"},{\"Name\":\"杭州鸿翔汽车租赁有限公司\",\"ChangeDate\":\"2017-01-12\"}],\"ImageUrl\":\"https://qccdata.qichacha.com/AutoImage/5681857e97f388bce24a85a524f85a51.jpg?x-oss-process=image/resize,w_120\",\"EntType\":\"0\",\"RecCap\":\"\"},\"Status\":\"200\",\"Message\":\"查询成功\"}";
        QccBasicResult result = JSON.parseObject(basic, QccBasicResult.class);
        System.out.println(result);
    }

    // 查询订单号
    private String OrderNumber;

    // 状态
    private String Status;

    // 信息
    private String Message;

    // 结果示例
    private Result Result;

    public static class Result implements Serializable {

        private static final long serialVersionUID = -8755131584408489306L;

        // 内部KeyNo
        private String KeyNo;

        // 公司名称
        private String Name;

        // 注册号
        private String No;

        // 登记机关
        private String BelongOrg;

        // 法人名
        private String OperName;

        // 成立日期
        private String StartDate;

        // 吊销日期
        private String EndDate;

        // 企业状态
        private String Status;

        // 省份
        private String Province;

        // 更新日期
        private String UpdatedDate;

        // 社会统一信用代码
        private String CreditCode;

        // 注册资本
        private String RegistCapi;

        // 企业类型
        private String EconKind;

        // 地址
        private String Address;

        // 经营范围
        private String Scope;

        // 营业开始日期
        private String TermStart;

        // 营业结束日期
        private String TeamEnd;

        // 发照日期
        private String CheckDate;

        // 组织机构代码
        private String OrgNo;

        // 是否IPO上市(0为未上市，1为上市)
        private String IsOnStock;

        // 上市公司代码
        private String StockNumber;

        // 上市类型
        private String StockType;

        // 企业类型，0-公司，1-社会组织 ，3-香港公司，4政府机构，5-台湾公司，6-基金会，7-医院，8-海外公司，9-律师事务所，10-学校 ，-1-其他
        private String EntType;

        // 实缴资本
        private String RecCap;

        // 曾用名
        private OriginalName[] OriginalName;

        public static class OriginalName implements Serializable {

            private static final long serialVersionUID = -2397046534264394419L;

            // 曾用名
            private String Name;

            // 变更日期
            private String ChangeDate;

            public String getName() {
                return Name;
            }

            public void setName(final String name) {
                Name = name;
            }

            public String getChangeDate() {
                return ChangeDate;
            }

            public void setChangeDate(final String changeDate) {
                ChangeDate = changeDate;
            }
        }

        public String getKeyNo() {
            return KeyNo;
        }

        public void setKeyNo(final String keyNo) {
            KeyNo = keyNo;
        }

        public String getName() {
            return Name;
        }

        public void setName(final String name) {
            Name = name;
        }

        public String getNo() {
            return No;
        }

        public void setNo(final String no) {
            No = no;
        }

        public String getBelongOrg() {
            return BelongOrg;
        }

        public void setBelongOrg(final String belongOrg) {
            BelongOrg = belongOrg;
        }

        public String getOperName() {
            return OperName;
        }

        public void setOperName(final String operName) {
            OperName = operName;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(final String startDate) {
            StartDate = startDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(final String endDate) {
            EndDate = endDate;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(final String status) {
            Status = status;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(final String province) {
            Province = province;
        }

        public String getUpdatedDate() {
            return UpdatedDate;
        }

        public void setUpdatedDate(final String updatedDate) {
            UpdatedDate = updatedDate;
        }

        public String getCreditCode() {
            return CreditCode;
        }

        public void setCreditCode(final String creditCode) {
            CreditCode = creditCode;
        }

        public String getRegistCapi() {
            return RegistCapi;
        }

        public void setRegistCapi(final String registCapi) {
            RegistCapi = registCapi;
        }

        public String getEconKind() {
            return EconKind;
        }

        public void setEconKind(final String econKind) {
            EconKind = econKind;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(final String address) {
            Address = address;
        }

        public String getScope() {
            return Scope;
        }

        public void setScope(final String scope) {
            Scope = scope;
        }

        public String getTermStart() {
            return TermStart;
        }

        public void setTermStart(final String termStart) {
            TermStart = termStart;
        }

        public String getTeamEnd() {
            return TeamEnd;
        }

        public void setTeamEnd(final String teamEnd) {
            TeamEnd = teamEnd;
        }

        public String getCheckDate() {
            return CheckDate;
        }

        public void setCheckDate(final String checkDate) {
            CheckDate = checkDate;
        }

        public String getOrgNo() {
            return OrgNo;
        }

        public void setOrgNo(final String orgNo) {
            OrgNo = orgNo;
        }

        public String getIsOnStock() {
            return IsOnStock;
        }

        public void setIsOnStock(final String isOnStock) {
            IsOnStock = isOnStock;
        }

        public String getStockNumber() {
            return StockNumber;
        }

        public void setStockNumber(final String stockNumber) {
            StockNumber = stockNumber;
        }

        public String getStockType() {
            return StockType;
        }

        public void setStockType(final String stockType) {
            StockType = stockType;
        }

        public String getEntType() {
            return EntType;
        }

        public void setEntType(final String entType) {
            EntType = entType;
        }

        public String getRecCap() {
            return RecCap;
        }

        public void setRecCap(final String recCap) {
            RecCap = recCap;
        }

        public QccBasicResult.Result.OriginalName[] getOriginalName() {
            return OriginalName;
        }

        public void setOriginalName(final QccBasicResult.Result.OriginalName[] originalName) {
            OriginalName = originalName;
        }
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(final String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(final String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(final String message) {
        Message = message;
    }

    public QccBasicResult.Result getResult() {
        return Result;
    }

    public void setResult(final QccBasicResult.Result result) {
        Result = result;
    }
}
