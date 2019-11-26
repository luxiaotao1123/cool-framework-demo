package com.cool.demo.common.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by vincent on 2019-11-19
 */
@Data
public class QccFullResult implements Serializable {

    public static void main(String[] args) {
        String full = "{\"OrderNumber\":\"ECI2019111914372580333304\",\"Result\":{\"PermissionInfo\":[],\"Penalty\":[],\"Exceptions\":[],\"ShiXinItems\":[],\"ZhiXingItems\":[],\"MPledge\":[],\"Liquidation\":null,\"Pledge\":[],\"SpotCheck\":[],\"CompanyTaxCreditItems\":[],\"CompanyProducts\":[],\"PermissionEciInfo\":[],\"PenaltyCreditChina\":[],\"Partners\":[{\"KeyNo\":\"p756c293d9b7b2207fb3a3140e267976\",\"StockName\":\"肖友康\",\"StockType\":\"自然人股东\",\"StockPercent\":\"92.00%\",\"ShouldCapi\":\"1840\",\"ShoudDate\":null,\"InvestType\":\"\",\"InvestName\":null,\"RealCapi\":\"\",\"CapiDate\":null,\"TagsList\":[\"大股东\",\"实际控制人\",\"最终受益人\"],\"FinalBenefitPercent\":\"92.00%\",\"RelatedProduct\":null,\"RelatedOrg\":null},{\"KeyNo\":\"pa62e15a60afb3316fdaf60823202cd6\",\"StockName\":\"范宏伟\",\"StockType\":\"自然人股东\",\"StockPercent\":\"8.00%\",\"ShouldCapi\":\"160\",\"ShoudDate\":null,\"InvestType\":\"\",\"InvestName\":null,\"RealCapi\":\"\",\"CapiDate\":null,\"TagsList\":[],\"FinalBenefitPercent\":\"8.00%\",\"RelatedProduct\":null,\"RelatedOrg\":null}],\"Employees\":[{\"Name\":\"肖友康\",\"Job\":\"执行董事兼总经理\"},{\"Name\":\"范宏伟\",\"Job\":\"监事\"}],\"Branches\":[],\"ChangeRecords\":[{\"ProjectName\":\"经营范围变更\",\"BeforeContent\":\"销售:汽车、汽车配件、汽车装饰品；服务:电动汽车的充电,汽车租赁,汽车事务咨询,汽车上牌、年检代理,网络预约出租汽车客运,新能源汽车充电站点的建设,车辆充、换电站的运营和维护。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"AfterContent\":\"销售:汽车、汽车配件、汽车装饰品、日用品、智能设备；食品经营；服务:电动汽车的充电,汽车租赁,汽车事务咨询,汽车上牌、年检代理,网络预约出租汽车客运,新能源汽车充电站点的建设,车辆充、换电站的运营和维护,临时停车服务；充电场站的开发建设；计算机系统集成、网络工程、通信技术、自动化控制系统的技术开发、计算机技术服务、计算机技术咨询、计算机软硬件开发。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"ChangeDate\":\"2019-07-01\"},{\"ProjectName\":\"经营范围变更\",\"BeforeContent\":\"销售:汽车、汽车配件、汽车装饰品；服务:电动汽车的充电,汽车租赁,汽车事务咨询,汽车上牌、年检代理,网络预约出租汽车客运。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"AfterContent\":\"销售:汽车、汽车配件、汽车装饰品；服务:电动汽车的充电,汽车租赁,汽车事务咨询,汽车上牌、年检代理,网络预约出租汽车客运,新能源汽车充电站点的建设,车辆充、换电站的运营和维护。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"ChangeDate\":\"2018-12-17\"},{\"ProjectName\":\"经营范围变更\",\"BeforeContent\":\"销售:汽车、汽车配件、汽车装饰品；服务:电动汽车的充电、汽车租赁、汽车事务咨询、汽车上牌、年检代理。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"AfterContent\":\"销售:汽车、汽车配件、汽车装饰品；服务:电动汽车的充电,汽车租赁,汽车事务咨询,汽车上牌、年检代理,网络预约出租汽车客运。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"ChangeDate\":\"2018-03-05\"},{\"ProjectName\":\"注册资本(金)变更\",\"BeforeContent\":\"注册资本:1000万；范宏伟:出资80万；肖友康*:出资920万\",\"AfterContent\":\"注册资本:2000万（+100%）；范宏伟:出资160万（+100%）；肖友康*:出资1840万（+100%）\",\"ChangeDate\":\"2017-04-26\"},{\"ProjectName\":\"经营范围变更\",\"BeforeContent\":\"汽车租赁服务；汽车销售；电动汽车的充电服务。\",\"AfterContent\":\"销售:汽车、汽车配件、汽车装饰品；服务:电动汽车的充电、汽车租赁、汽车事务咨询、汽车上牌、年检代理。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"ChangeDate\":\"2017-04-26\"},{\"ProjectName\":\"名称变更\",\"BeforeContent\":\"杭州递递叭叭新能源汽车租赁有限公司\",\"AfterContent\":\"杭州递递叭叭新能源汽车有限公司\",\"ChangeDate\":\"2017-04-26\"},{\"ProjectName\":\"名称变更\",\"BeforeContent\":\"企业名称: 杭州鸿翔汽车租赁有限公司；   行业: 汽车批发；   一般经营项目: 汽车租赁服务；汽车销售。\",\"AfterContent\":\"企业名称: 杭州递递叭叭新能源汽车租赁有限公司；   行业: 汽车租赁；   一般经营项目: 汽车租赁服务；汽车销售；电动汽车的充电服务。\",\"ChangeDate\":\"2017-01-12\"}],\"ContactInfo\":{\"WebSite\":[{\"Name\":null,\"Url\":\"http://www.hzddbb.com\"}],\"PhoneNumber\":\"0571-86287878\",\"Email\":null},\"Industry\":{\"IndustryCode\":\"L\",\"Industry\":\"租赁和商务服务业\",\"SubIndustryCode\":\"71\",\"SubIndustry\":\"租赁业\",\"MiddleCategoryCode\":\"711\",\"MiddleCategory\":\"机械设备经营租赁\",\"SmallCategoryCode\":\"7111\",\"SmallCategory\":\"汽车租赁\"},\"Area\":{\"Province\":\"浙江省\",\"City\":\"杭州市\",\"County\":\"余杭区\"},\"InsuredCount\":\"49\",\"EnglishName\":null,\"PersonScope\":\"\",\"TagList\":[{\"Type\":\"903\",\"Name\":\"存续\"},{\"Type\":\"99\",\"Name\":\"曾用名\"}],\"ARContactList\":[{\"ContactNo\":\"057186287878\",\"EmailAddress\":\"\",\"Address\":\"浙江省杭州市余杭区南苑街道人民大道750#二楼\"}],\"EconKindCodeList\":[\"10\"],\"KeyNo\":\"5681857e97f388bce24a85a524f85a51\",\"Name\":\"杭州递递叭叭新能源汽车有限公司\",\"No\":\"330184000502846\",\"BelongOrg\":\"杭州市余杭区市场监督管理局\",\"OperName\":\"肖友康\",\"StartDate\":\"2016-07-28 00:00:00\",\"EndDate\":\"\",\"Status\":\"存续\",\"Province\":\"ZJ\",\"UpdatedDate\":\"2019-11-12 14:20:30\",\"CreditCode\":\"91330110MA27YAMJ54\",\"RegistCapi\":\"2000万元人民币\",\"EconKind\":\"有限责任公司(自然人投资或控股)\",\"Address\":\"浙江省杭州市余杭区南苑街道人民大道750#二楼\",\"Scope\":\"销售:汽车、汽车配件、汽车装饰品、日用品、智能设备;食品经营;服务:电动汽车的充电,汽车租赁,汽车事务咨询,汽车上牌、年检代理,网络预约出租汽车客运,新能源汽车充电站点的建设,车辆充、换电站的运营和维护,临时停车服务;充电场站的开发建设;计算机系统集成、网络工程、通信技术、自动化控制系统的技术开发、计算机技术服务、计算机技术咨询、计算机软硬件开发。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"TermStart\":\"2016-07-28 00:00:00\",\"TeamEnd\":\"9999-09-09 00:00:00\",\"CheckDate\":\"2019-07-01 00:00:00\",\"OrgNo\":\"MA27YAMJ-5\",\"IsOnStock\":\"0\",\"StockNumber\":null,\"StockType\":null,\"OriginalName\":[{\"Name\":\"杭州递递叭叭新能源汽车租赁有限公司\",\"ChangeDate\":\"2017-04-26\"},{\"Name\":\"杭州鸿翔汽车租赁有限公司\",\"ChangeDate\":\"2017-01-12\"}],\"ImageUrl\":\"https://qccdata.qichacha.com/AutoImage/5681857e97f388bce24a85a524f85a51.jpg?x-oss-process=image/resize,w_120\",\"EntType\":\"0\",\"RecCap\":\"\"},\"Status\":\"200\",\"Message\":\"查询成功\"}\n";
        System.out.println(full);
        QccFullResult result = JSON.parseObject(full, QccFullResult.class);
        System.out.println(result);
    }

    private static final long serialVersionUID = 3758876207792620536L;

    // 查询订单号
    private String OrderNumber;

    // 状态
    private String Status;

    // 信息
    private String Message;

    // 结果示例
    private QccFullResult.Result Result;

    public static class Result implements Serializable {

        private static final long serialVersionUID = -2468030529317903339L;

        // 行政许可【信用中国】
        public PermissionInfo[] PermissionInfo;

        // 行政处罚
        public Penalty[] Penalty;

        // 经营异常
        public Exceptions[] Exceptions;

        // 失信
        public ShiXinItems[] ShiXinItems;

        // 执行
        public ZhiXingItems[] ZhiXingItems;

        // 动产抵押
        public MPledge[] MPledge;

        // 清算
        public Liquidation[] Liquidation;

        // 股权出质
        public Pledge[] Pledge;

        // 抽查检查
        public SpotCheck[] SpotCheck;

        // 纳税信用等级
        public CompanyTaxCreditItems[] CompanyTaxCreditItems;

        // 公司产品
        public CompanyProducts[] CompanyProducts;

        // 行政许可【工商局】
        public PermissionEciInfo[] PermissionEciInfo;

        // 行政处罚【信用中国】
        public PenaltyCreditChina[] PenaltyCreditChina;

        // 投资人及出资信息
        public Partners[] Partners;

        // 主要人员
        public Employees[] Employees;

        // 分支机构
        public Branches[] Branches;

        // 变更信息
        public ChangeRecords[] ChangeRecords;

        // 联系信息
        public ContactInfo ContactInfo;

        // 行业分类数据
        public Industry Industry;

        // 区域
        public Area Area;

        // 参保人数
        public String InsuredCount;

        // 英文名
        public String EnglishName;

        // 人员规模
        public String PersonScope;

        // 标签列表
        public TagList[] TagList;

        // 最新企业年报中的联系方式
        public ARContactList[] ARContactList;

        // 企业类型数组(具体枚举值请查看附件)
        public String[] EconKindCodeList;

        // 根据此字段获取详细信息
        public String KeyNo;

        // 公司名称
        public String Name;

        // 注册号
        public String No;

        // 登记机关
        public String BelongOrg;

        // 法人
        public String OperName;

        // 成立日期
        public String StartDate;

        // 吊销日期
        public String EndDate;

        // 状态
        public String Status;

        // 省份
        public String Province;

        // 更新日期
        public String UpdatedDate;

        // 信用代码
        public String CreditCode;

        // 注册资本
        public String RegistCapi;

        // 类型
        public String EconKind;

        // 地址
        public String Address;

        // 营业范围
        public String Scope;

        // 营业期限始
        public String TermStart;

        // 营业期限至
        public String TeamEnd;

        // 发照日期
        public String CheckDate;

        // 组织机构代码
        public String OrgNo;

        // 是否上市
        public String IsOnStock;

        // 证券号
        public String StockNumber;

        // 证券类型
        public String StockType;

        // 曾用名
        public OriginalName[] OriginalName;

        // Logo地址
        public String ImageUrl;

        // 企业类型 0-企业（包括个体工商户），1-社会组织 ，3-香港公司，4-政府机构，5-台湾公司，6-基金会，7-医院，8-海外公司，9-律师事务所，10-学校 ，-1-其他
        public String EntType;

        // 实缴资本
        public String RecCap;

        // 行政许可【信用中国】
        public static class PermissionInfo {

            // 项目名称
            public String Name;

            // 地域
            public String Province;

            // 决定日期
            public String Liandate;

            // 决定文书号
            public String CaseNo;

        }

        // 行政处罚
        public static class Penalty {

            // 行政处罚决定书文号
            public String DocNo;

            // 违法行为类型
            public String PenaltyType;

            // 行政处罚决定机关名称
            public String OfficeName;

            // 行政处罚内容
            public String Content;

            // 作出行政处罚决定日期
            public String PenaltyDate;

            // 作出行政公示日期
            public String PublicDate;

            // 备注
            public String Remark;

        }

        // 经营异常
        public static class Exceptions {

            // 列入经营异常名录原因
            public String AddReason;

            // 列入日期
            public String AddDate;

            // 移出经营异常名录原因
            public String RomoveReason;

            // 移出日期
            public String RemoveDate;

            // 作出决定机关
            public String DecisionOffice;

            // 移除决定机关
            public String RemoveDecisionOffice;

        }

        // 失信
        public static class ShiXinItems {

            // 公司名
            public String Name;

            // 立案日期
            public String Liandate;

            // 立案文书号
            public String Anno;

            // 组织机构代码
            public String Orgno;

            // 执行依据文号
            public String Executeno;

            // 发布时间
            public String Publicdate;

            // 被执行人的履行情况
            public String Executestatus;

            // 行为备注
            public String Actionremark;

            // 执行法院
            public String Executegov;

        }

        public static class ZhiXingItems {

        }

        public static class MPledge {

        }

        public static class Liquidation {

        }

        public static class Pledge {

        }

        public static class SpotCheck {

        }

        public static class CompanyTaxCreditItems {

        }

        public static class CompanyProducts {

        }

        public static class PermissionEciInfo {

        }

        public static class PenaltyCreditChina {

        }

        public static class Partners {

        }

        public static class Employees {

        }

        public static class Branches {

        }

        public static class ChangeRecords {

        }

        public static class ContactInfo {

            // 网址
            public WebSite[] WebSite;

            // 联系电话
            public String PhoneNumber;

            // 邮箱
            public String Email;

            public static class WebSite {

                // 网址名称
                public String Name;

                // 网址地址
                public String Url;

            }

        }

        public static class Industry {

        }

        public static class Area {

        }

        public static class TagList {

        }

        public static class ARContactList {

        }

        public static class OriginalName {

            // 名称
            private String Name;

            // 变更日期
            private String ChangeDate;

        }

    }

}

