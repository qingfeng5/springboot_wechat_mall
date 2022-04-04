<html>
<#--把开头内容放到header中，改成公用-->
<#include "../common/header.ftl">
<#--<head>-->
<#--    <meta charset="utf-8">-->
<#--    <title>卖家后端管理系统</title>-->
<#--    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">-->
<#--    <link rel="stylesheet" href="style.css">-->
<#--    如果不加server.servlet.context-path=sell这段话，会导致边框出现问题-->
<#--    server.servlet.context-path=sell <link rel="stylesheet" href="/sell/css/style.css">-->
<#--    <link href="https://cdn.bootcss.com/bootstrap-fileinput/4.4.7/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />-->
<#--</head>-->
<body>

    <!--最外层的模板套子-->
    <div id="wrapper" class="toggled">

        <#--边栏sidebar-->
        <!--引入标签include-->
        <#include "../common/nav.ftl">

        <#--主要内容content-->
        <div id="page-content-wrapper">
<#--            流动布局，取消按钮就能显示出来-->
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>订单id</th>
                        <th>姓名</th>
                        <th>手机号</th>
                        <th>地址</th>
                        <th>金额</th>
                        <th>订单状态</th>
                        <th>支付状态</th>
                        <th>创建时间</th>
                        <th colspan="2">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list orderDTOPage.content as orderDTO>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
<#--                        <td>${orderDTO.orderStatus}</td>-->
<#--                        <td>${orderDTO.payStatus}</td>-->
<#--                        <td>${orderDTO.createTime}</td>-->
<#--                        <td>详情</td>-->
<#--                        <td>取消</td>-->
                        <td>${orderDTO.getOrderStatusEnum().message}</td>
                        <td>${orderDTO.getPayStatusEnum().message}</td>
                        <td>${orderDTO.createTime}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                        <td>
                            <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                    </table>
            </div>

            <!--分页-->
            <div class="col-md-12 column">
                <ul class="pagination pull-right">
                    <!--如果当前页小于或者等于1，那么“上一页按钮不可以点，同理，上一页也是这样”-->
                    <#if currentPage lte 1>
                        <!--灰掉状态不可点-->
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <!--上一页的链接时当前页减一-->
                        <!--动态传进size，这个页面显示数据条数-->
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>
                    <!--从1开始计数,1到总数的页数，遍历循环，显示数据库中分页有几页-->
                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <!--如果等于当前页，鼠标属于不可用的状态-->
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <!--如果当前页大于或者等于总数，那么“上一页按钮不可以点，同理，上一页也是这样”-->
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                </ul>
            </div>

        </div>
    </div>
  </div>
 </div>


    </body>
</html>



