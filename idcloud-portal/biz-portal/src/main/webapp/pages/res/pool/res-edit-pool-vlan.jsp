<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <!-- 编辑主机资源池信息 -->
  <div id="editVlanRespoolWindow">
          <div>编辑VLAN池</div>
          <div id="editVlanRespoolForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resPoolSid" id="edit-resPoolSid-vlan"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="120px"><font style="color:red">*</font>资源池名称:</td>
                         <td align="left"><input type="text" data-name="resPoolName" id="edit-resPoolName-vlan"/></td>
                         <td align="right" width="110px">资源池状态:</td>
                         <td align="left">
                         	<div data-name="status" id="edit-status-vlan"></div>
                         </td>
                         <td align="right" width="130px">性能保障等级:</td>
                         <td align="left">
                         	<div data-name="perfLevel" id="edit-perfLevel-vlan"></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">分配策略:</td>
                         <td align="left">
                         	<div data-name="allocationPolicy" id="edit-allocationPolicy-vlan"></div>
                         </td>
                         <td align="right">换算公式:</td>
                         <td align="left">
                         	<input type="text" data-name="conversionFormula" id="edit-conversionFormula-vlan"/>
                         </td>
                         <td align="right">可分配阀值:</td>
                         <td align="left">
                         	<input type="text" data-name="allocationThreshold" id="edit-allocationThreshold-vlan"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">VLAN类型:</td>
                         <td align="left" colspan="5">
                         	<div data-name="vlanType" id="edit-vlanType-vlan"></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right"><font style="color:red">*</font>操作处理器:</td>
                         <td align="left" colspan="5">
                         	<input type="text" data-name="operationHandler" id="edit-operationHandler-vlan"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">资源池描述:</td>
                         <td align="left" colspan="5">
                         	<textarea data-name="description" id="edit-description-vlan"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="6">
          				 	<input style="margin-right: 5px;" onclick="editRespoolVlanSubmit()" type="button" id="editVlanRespoolSave" value="保存" />
		              		<input id="editVlanRespoolCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>   