(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["equipment"],{"158d":function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"equipment"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12}},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[t._v("资产管理")]),a("el-breadcrumb-item",[t._v("设备信息")])],1)],1),a("el-col",{attrs:{span:10,align:"right"}},[t.getUserJurisdiction("AT09")?a("a",{attrs:{href:t.hostname+"/pvams/device/export/"+t.stationId}},[a("el-button",{attrs:{size:"small"}},[t._v("导出信息")])],1):t._e()]),a("el-col",{attrs:{span:2,align:"right"}},[t.getUserJurisdiction("AT09")?a("el-upload",{staticClass:"upload-demo",attrs:{"show-file-list":!1,action:"/pvams/device/import/"+t.stationId,"on-success":function(e){return t.uploadSuccess(e)},"before-upload":function(e){return t.beforeAvatarUpload(e)}}},[a("el-button",{attrs:{size:"small",type:"primary"}},[t._v("导入信息")])],1):t._e()],1)],1),a("div",{staticClass:"table",style:{margin:"20px 0"}},[a("el-table",{attrs:{"header-cell-class-name":"table-th",data:t.tableList,border:"",stripe:""}},[a("el-table-column",{attrs:{type:"index",width:"50px",align:"center",label:"序号"}}),t._l(t.columns,function(t,e){return a("el-table-column",{key:e,attrs:{align:"center",prop:t.prop,label:t.label,width:t.width}})})],2)],1),a("div",[a("el-pagination",{attrs:{align:"right",small:"","page-size":10,layout:"prev, pager, next",total:t.total},on:{"current-change":t.handleCurrentChange}})],1)],1)},r=[],i=(a("96cf"),a("3b8d")),n=(a("6762"),a("2fdb"),a("f121")),o=a("bfe6"),l={mixins:[o["a"]],data:function(){return{tableList:[1],columns:[{prop:"deviceName",label:"设备名称"},{prop:"number",label:"数量"},{prop:"type",label:"类型"},{prop:"model",label:"型号"},{prop:"supplier",label:"供应商"},{prop:"contact",label:"联系人"},{prop:"phone",label:"联系方式"},{prop:"warrantyStartDate",label:"质保起始日期",width:"120px"},{prop:"warrantyEndDate",label:"质保终止日期",width:"120px"},{prop:"param",label:"主要参数",width:"100px"},{prop:"remark",label:"备注"}],stationId:"",total:0,hostname:""}},created:function(){this.hostname=n["a"].HOST.pvamsDomain||"";var t=this.$store.state.getTreeId.treeId;t.id&&t.id.includes("s")&&(this.stationId=t.id.slice(1,t.id.length),this.stationId&&this.getTableList())},watch:{getStateData:function(t){var e=this.$store.state.getTreeId.treeId;e.id&&e.id.includes("s")&&(this.stationId=e.id.slice(1,e.id.length),this.stationId&&this.getTableList())}},computed:{getStateData:function(){return this.$store.state.getTreeId.treeId}},methods:{getTableList:function(){var t=Object(i["a"])(regeneratorRuntime.mark(function t(e){var a,s,r,i,n,o;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return this.loadingOpen(),a={stationId:this.stationId,pageNo:e,pageSize:10},t.next=4,this.axios("/pvams/station/getStationDevice",{params:a});case 4:s=t.sent,r=s.data.data,r=void 0===r?{}:r,i=r.deviceList,n=r.page,n=void 0===n?{}:n,o=n.count,this.loadingClose(),i&&(this.tableList=i||[],this.total=o);case 12:case"end":return t.stop()}},t,this)}));function e(e){return t.apply(this,arguments)}return e}(),handleCurrentChange:function(t){this.getTableList(t)},uploadSuccess:function(t){200===t.code?(this.$message({type:"success",message:"上传成功"}),this.getTableList(1)):this.$message({type:"error",message:t.message})},beforeAvatarUpload:function(t){var e=t.size/1024/1024<20;return e||this.$message.error("上传文件大小不得超过20mb!"),e}}},p=l,c=a("2877"),d=Object(c["a"])(p,s,r,!1,null,"c136aadc",null);e["default"]=d.exports},f121:function(t,e,a){"use strict";var s={};a.r(s),a.d(s,"HOST",function(){return n}),a.d(s,"ENV",function(){return o});var r={};a.r(r),a.d(r,"HOST",function(){return l}),a.d(r,"ENV",function(){return p});var i,n={pvamsDomain:"http://101.37.67.138:8088"},o="sit",l={pvamsDomain:"http://101.37.67.138:8088"},p="prd";i="101.37.67.138"===window.location.hostname?r:s;e["a"]=i}}]);
//# sourceMappingURL=equipment.010c12a0.js.map