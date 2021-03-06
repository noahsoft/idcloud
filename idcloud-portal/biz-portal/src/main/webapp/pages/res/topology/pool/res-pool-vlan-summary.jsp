<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#clusterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#clusterBaseInfo tr:hover{
		background:#E6E6E6;
	}  
</style>
  <div style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="position:relative;width:98%;height:140px;margin-left:1%;">
  		<div class="customPanel" style="position:absolute;top:0px;left:0px;width:47%;height:435px;">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="position:absolute;right:5px;cursor:pointer" onclick="popEditRespoolVlanWindow()"><i class="icons-blue icon-edit"></i></font>
  		    </div>   
	        <div>
	        	<table id="clusterBaseInfo" border="0" width="100%" cellspacing="2" cellpadding="0">
	        		<tr>
	        			<td align="right" width="100px" height="25px">VLAN类型：</td>
	        			<td align="left">
	        				<span id="vlanType-vlan"></span>
	        			</td>
	        		</tr>
	        		
	        		<tr>
	        			<td align="right" height="25px">分配策略：</td>
	        			<td align="left">
	        				<span id="allocationPolicy-vlan"></span>
	        			</td>
	        		</tr>
	        	
	        		<tr>
	        			<td align="right" height="25px">性能保障等级：</td>
	        			<td align="left">
	        				<span id="perfLevel-vlan"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">资源池状态：</td>
	        			<td align="left">
	        				<span id="statusName-vlan"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">操作处理器：</td>
	        			<td align="left">
	        				<span id="operationHandler-vlan"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">资源池描述：</td>
	        			<td align="left">
	        				<span id="description-vlan"></span>
	        			</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
	  		<div class="customPanel" style="position:absolute;left:48%;top:0px;width:51.8%;height:90px;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作  <font style="position:absolute;right:5px;"><i class="icons-blue icon-help"></i></font>
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddVlanToPoolWindow()"><i class="icon-desktop"></i>&nbsp;添加VLAN入池</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeVlanRespool()"><i class="icon-cancel-3"></i>&nbsp;删除资源池</span></td>
	        			<td align="left" height="25px"></td>
	        		
	        		</tr>
	    
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	
  	<div style="width:98%;height:350px;margin-left:1%;position:relative;">
  	    
  		<div class="customPanel" style="position:absolute;left:48%;top:-35px;width:51.8%;height:330px;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='vlanChart' style="width:80%; height:300px;border:0px;"></div>	
        </div>
  		</div>
  		
  	</div>
  </div>
  <%@ include file="../../pool/res-edit-pool-vlan.jsp"%>
  <%@ include file="../../pool/res-add-vlan-to-pool-vlan.jsp"%>    	
<script type="text/javascript">

     function initRespoolSummary(){
 		// 初始化统计信息值
   	 	var vlan = new vlanConfigMgtModel();
   		var vlanData =  vlan.VlanResourcesStatistics();
   		 
    	 // 初始化资源统计值
    	 var respool = new editResPoolModel();
    	 // 初始化vlan基本信息值
    	 respool.setVlanResPool();
    	 // 初始化vlanwindow
    	 respool.initPopVlanWindow();
    	 // 初始化vlan验证
    	 respool.initVlanValidator();
    	 
    	 var data = respool.searchResPool(resTopologySid);
    	 
    	 // 初始化vlan添加池model
    	 var vlanToPool = new addNetworkToResPoolModel();
    	 vlanToPool.initfindVlanAddToPoolDatagrid();
    	 vlanToPool.initVlanPopWindow();
    	 vlanToPool.searchfindVlanAddToPoolData();
    	 
    	 initVlanChart(vlanData);
     } 

	
	// 初始化vlan图表
	 function initVlanChart(data){
		 var source =
         {
             datatype: "json",
             datafields: [
                 { name: 'name' },
                 { name: 'value' }
             ],
             localdata: data.attr
         };
         var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
         // prepare jqxChart settings
         var settings = {
             title: "VLAN资源统计",
             description: "(总数:"+data.vlanCount+"个)",
             enableAnimations: true,
             showLegend: true,
             showBorderLine: false,
             legendPosition: { left: 200, top: 140, width: 50, height: 50 },
             padding: { left: 5, top: 5, right: 5, bottom: 5 },
             titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
             source: dataAdapter,
             colorScheme: 'scheme01',
             seriesGroups:
                 [
                     {
                         type: 'pie',
                         showLabels: true,
                         series:
                             [
                                 { 
                                     dataField: 'value',
                                     displayText: 'name',
                                     labelRadius: 120,
                                     initialAngle: 15,
                                     radius: 100,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '(个)', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#vlanChart').jqxChart(settings);
	 }
	 
</script>

