(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["imptMn"],{1956:function(t,e,a){},"4a7f":function(t,e,a){"use strict";var s=a("1956"),r=a.n(s);r.a},d353:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"impt-mn"},[a("el-form",{ref:"searchform",attrs:{model:t.searchform,size:"small","label-width":"100px"}},[a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"信息类型："}},[a("el-select",{attrs:{clearable:"",placeholder:"请选择"},on:{change:t.changeQuery},model:{value:t.searchform.type,callback:function(e){t.$set(t.searchform,"type",e)},expression:"searchform.type"}},t._l(t.infoTypeList,function(t,e){return a("el-option",{key:e,attrs:{label:t.label,value:t.value}})}),1)],1)],1),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"提报人："}},[a("el-select",{attrs:{clearable:"",placeholder:"请选择"},on:{change:t.changeQuery},model:{value:t.searchform.name,callback:function(e){t.$set(t.searchform,"name",e)},expression:"searchform.name"}},t._l(t.options2,function(t,e){return a("el-option",{key:e,attrs:{label:t.realname,value:t.realname}})}),1)],1)],1),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"审批状态："}},[a("el-select",{attrs:{clearable:"",placeholder:"请选择"},on:{change:t.changeQuery},model:{value:t.searchform.status,callback:function(e){t.$set(t.searchform,"status",e)},expression:"searchform.status"}},t._l(t.statusList,function(t,e){return a("el-option",{key:e,attrs:{label:t.label,value:t.value}})}),1)],1)],1),a("el-col",{attrs:{span:10}},[a("el-form-item",{attrs:{label:"提报时间："}},[a("el-date-picker",{style:{width:"100%"},attrs:{type:"daterange",format:"yyyy-MM-dd","value-format":"yyyy-MM-dd","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期"},on:{change:t.changeQuery},model:{value:t.searchform.startDate,callback:function(e){t.$set(t.searchform,"startDate",e)},expression:"searchform.startDate"}})],1)],1),a("el-col",{attrs:{span:13,align:"right"}},[a("el-button",{attrs:{size:"small",type:""},on:{click:t.clickAdd}},[t._v("新建维护")])],1)],1),a("div",{staticClass:"table",style:{margin:"20px 0"}},[a("el-table",{attrs:{"header-cell-class-name":"table-th",data:t.tableList,border:"",stripe:""}},[a("el-table-column",{attrs:{align:"center",width:"80px",type:"index",label:"序号"}}),t._l(t.columns,function(t,e){return a("el-table-column",{key:e,attrs:{align:"center",type:t.type,prop:t.prop,label:t.label,width:t.width}})}),a("el-table-column",{attrs:{align:"center",width:"100px",label:"审批状态"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",{staticClass:"status-sty",style:{color:0===e.row.status?"#3333FF":1===e.row.status?"#008000":2===e.row.status?"#CC9952":3===e.row.status?"#999999":"#000"}},[a("i",{style:{backgroundColor:0===e.row.status?"#3333FF":1===e.row.status?"#008000":2===e.row.status?"#CC9952":3===e.row.status?"#999999":"#000"}}),t._v(" "+t._s(e.row.statusLabel))])]}}])}),a("el-table-column",{attrs:{align:"center",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[0==e.row.status&&t.getUserJurisdiction("AT15")?a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return t.clickEdit(e.row,1)}}},[t._v("审批")]):t._e(),3==e.row.status?a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return t.clickEdit(e.row,2)}}},[t._v("重报")]):t._e(),t.getUserJurisdiction("AT16")?a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return t.clickDet(e.row)}}},[t._v("删除")]):t._e()]}}])})],2)],1),a("div",[a("el-pagination",{attrs:{align:"right",small:"","page-size":20,layout:"prev, pager, next",total:t.total},on:{"current-change":t.handleCurrentChange}})],1),a("div",{staticClass:"dialog"},[a("el-dialog",{attrs:{title:t.isEdit?"审批重要维护信息":"新建重要维护信息",visible:t.isVisibleAdd,width:"50%"},on:{"update:visible":function(e){t.isVisibleAdd=e},close:function(e){return t.closeAddForm("formAdd")}}},[a("div",{staticClass:"main"},[a("el-form",{ref:"formAdd",attrs:{model:t.formAdd,size:"small","label-width":"80px"}},[a("el-row",[a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{label:"提报人"}},[a("el-input",{attrs:{disabled:"",placeholder:""},model:{value:t.formAdd.presenter,callback:function(e){t.$set(t.formAdd,"presenter",e)},expression:"formAdd.presenter"}})],1)],1)],1),a("el-row",[a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{label:"提报时间"}},[t.isUpdate?a("span",[t._v(t._s(t.formAdd.presentDate||""))]):a("el-date-picker",{style:{width:"100%"},attrs:{disabled:!0,type:"datetime",format:"yyyy-MM-dd hh:mm:ss","value-format":"yyyy-MM-dd hh:mm:ss",placeholder:"选择日期时间"},model:{value:t.formAdd.presentDate,callback:function(e){t.$set(t.formAdd,"presentDate",e)},expression:"formAdd.presentDate"}})],1)],1),a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{label:"信息类型"}},[t.isUpdate?a("span",[t._v(t._s(t.formAdd.typeName||""))]):a("el-select",{attrs:{placeholder:"请选择"},model:{value:t.formAdd.type,callback:function(e){t.$set(t.formAdd,"type",e)},expression:"formAdd.type"}},t._l(t.infoTypeList,function(t,e){return a("el-option",{key:e,attrs:{label:t.label,value:t.value}})}),1)],1)],1)],1),a("el-row",[a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{label:"开始时间"}},[t.isUpdate?a("span",[t._v(t._s(t.formAdd.startDate||""))]):a("el-date-picker",{style:{width:"100%"},attrs:{type:"datetime",format:"yyyy-MM-dd HH:mm:ss","value-format":"yyyy-MM-dd HH:mm:ss",placeholder:"选择日期时间"},model:{value:t.formAdd.startDate,callback:function(e){t.$set(t.formAdd,"startDate",e)},expression:"formAdd.startDate"}})],1)],1),a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{label:"结束时间"}},[t.isUpdate?a("span",[t._v(t._s(t.formAdd.endDate))]):a("el-date-picker",{style:{width:"100%"},attrs:{type:"datetime",format:"yyyy-MM-dd HH:mm:ss","value-format":"yyyy-MM-dd HH:mm:ss",placeholder:"选择日期时间"},model:{value:t.formAdd.endDate,callback:function(e){t.$set(t.formAdd,"endDate",e)},expression:"formAdd.endDate"}})],1)],1)],1),a("el-col",{attrs:{span:24}},[a("el-form-item",{attrs:{label:"主题"}},[t.isUpdate?a("span",[t._v(t._s(t.formAdd.subject))]):a("el-input",{attrs:{type:"textarea",rows:2,placeholder:""},model:{value:t.formAdd.subject,callback:function(e){t.$set(t.formAdd,"subject",e)},expression:"formAdd.subject"}})],1)],1),a("el-col",{attrs:{span:24}},[a("el-form-item",{attrs:{label:"信息详情"}},[t.isUpdate?a("span",[t._v(t._s(t.formAdd.infoDetail))]):a("el-input",{attrs:{type:"textarea",rows:2,placeholder:""},model:{value:t.formAdd.infoDetail,callback:function(e){t.$set(t.formAdd,"infoDetail",e)},expression:"formAdd.infoDetail"}})],1)],1),t.isEdit?a("el-col",{attrs:{span:24}},[a("el-form-item",{attrs:{label:"审批"}},[a("el-col",{attrs:{span:12}},[a("p",[t._v("审批状态："),a("span",[t._v("未审批")])])]),a("el-col",{attrs:{span:12}},[a("el-radio",{attrs:{label:1},model:{value:t.formAdd.radiostatus,callback:function(e){t.$set(t.formAdd,"radiostatus",e)},expression:"formAdd.radiostatus"}},[t._v("通过")]),a("el-radio",{attrs:{label:2},model:{value:t.formAdd.radiostatus,callback:function(e){t.$set(t.formAdd,"radiostatus",e)},expression:"formAdd.radiostatus"}},[t._v("拒绝")])],1)],1)],1):t._e()],1)],1),a("div",{attrs:{slot:"footer",align:"center"},slot:"footer"},[a("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(e){return t.clickConfirm("formAdd")}}},[t._v("提交")]),a("el-button",{attrs:{size:"small"},on:{click:function(e){t.isVisibleAdd=!1}}},[t._v("取 消")])],1)])],1)],1)},r=[],l=(a("ac6a"),a("cebc")),i=(a("96cf"),a("3b8d")),n=(a("6762"),a("2fdb"),a("c1df")),o=a.n(n),d=a("bfe6"),c={mixins:[d["a"]],data:function(){return{isVisibleAdd:!1,searchform:{},total:0,options2:[],isEdit:!1,formAdd:{},tableList:[],realname:"",isUpdate:!1,userId:"",statusList:[{label:"新提报",value:0},{label:"已通过",value:1},{label:"已拒绝",value:2},{label:"已过期",value:3}],stationId:"",infoTypeList:[{label:"调度命令",value:1},{label:"重要隐患",value:2},{label:"重要工作安排",value:3},{label:"事故信息",value:4}],columns:[{prop:"subject",label:"主题",width:""},{prop:"typeName",label:"类型",width:""},{prop:"presenter",label:"提报人",width:""},{prop:"presentDate",label:"提报时间",width:"160px"},{prop:"startDate",label:"有效期开始时间",width:"160px"},{prop:"endDate",label:"有效期结束时间",width:"160px"}]}},created:function(){this.realname=localStorage.getItem("realname"),this.userId=localStorage.getItem("userId");var t=this.$store.state.getTreeId.treeId;t.id&&t.id.includes("s")&&(this.stationId=t.id.slice(1,t.id.length),this.stationId&&(this.getTableList(1),this.getRealnameData()))},watch:{getStateData:function(t){var e=this.$store.state.getTreeId.treeId;e.id&&e.id.includes("s")&&(this.stationId=e.id.slice(1,e.id.length),this.stationId&&(this.getTableList(1),this.getRealnameData()))}},computed:{getStateData:function(){return this.$store.state.getTreeId.treeId}},methods:{getTableList:function(){var t=Object(i["a"])(regeneratorRuntime.mark(function t(e){var a,s,r,i,n,o,d,c,u,m,f,p=this;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return this.loadingOpen(),a=this.searchform.startDate&&0!==this.searchform.startDate.length&&this.searchform.startDate[0],s=this.searchform.startDate&&0!==this.searchform.startDate.length&&this.searchform.startDate[1],r=Object(l["a"])({},this.searchform,{startDate:a,endDate:s,stationId:this.stationId,pageNo:e,pageSize:20}),t.next=6,this.axios.post("/pvams/info/query",r);case 6:i=t.sent,n=i.data,n=void 0===n?{}:n,o=n.data,d=o.infoList,c=void 0===d?[]:d,u=o.page,u=void 0===u?{}:u,m=u.count,f=void 0===m?0:m,this.loadingClose(),this.total=f,c.forEach(function(t){if(t.type){var e=p.infoTypeList.filter(function(e){return e.value===t.type});t.typeName=e&&e[0].label}if(t.status||0===t.status){var a=p.statusList.filter(function(e){return e.value===t.status});t.statusLabel=a&&a[0].label}}),this.tableList=c;case 16:case"end":return t.stop()}},t,this)}));function e(e){return t.apply(this,arguments)}return e}(),getRealnameData:function(){var t=Object(i["a"])(regeneratorRuntime.mark(function t(){var e,a,s,r,l;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,this.axios("/pvams/info/getPresenter/".concat(this.stationId));case 2:e=t.sent,a=e.data,a=void 0===a?{}:a,s=a.data,r=void 0===s?[]:s,l=a.code,200===l&&(this.options2=r);case 7:case"end":return t.stop()}},t,this)}));function e(){return t.apply(this,arguments)}return e}(),changeQuery:function(){this.getTableList(1)},handleCurrentChange:function(t){this.getTableList(t)},closeAddForm:function(t){this.$refs[t].resetFields()},clickAdd:function(){this.formAdd={presenter:this.realname,presentDate:o()(new Date).format("YYYY-MM-DD hh:mm:ss")},this.isEdit=!1,this.isVisibleAdd=!0},clickConfirm:function(t){var e=this;this.$refs[t].validate(function(){var t=Object(i["a"])(regeneratorRuntime.mark(function t(a){var s,r,i,n,o,d,c,u;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:if(!a){t.next=26;break}if(s=Object(l["a"])({presentUserId:e.userId,stationId:e.stationId},e.formAdd),e.formAdd.status||0===e.formAdd.status){t.next=10;break}return t.next=5,e.axios.post("/pvams/info/add",s);case 5:r=t.sent,i=r.data.code,200===i&&(e.getTableList(1),e.isVisibleAdd=!1),t.next=26;break;case 10:if(0!==e.formAdd.status){t.next=19;break}return n={id:e.formAdd.id,status:e.formAdd.radiostatus,approveUserId:e.userId},t.next=14,e.axios("/pvams/info/approve",{params:n});case 14:o=t.sent,d=o.data.code,200===d&&(e.getTableList(1),e.isVisibleAdd=!1),t.next=26;break;case 19:if(3!==e.formAdd.status){t.next=26;break}return t.next=22,e.axios.post("/pvams/info/update",s);case 22:c=t.sent,u=c.data.code,200===u&&(e.getTableList(1),e.isVisibleAdd=!1);case 26:case"end":return t.stop()}},t)}));return function(e){return t.apply(this,arguments)}}())},clickEdit:function(t,e){this.isEdit=1===e,this.formAdd=Object(l["a"])({},t),this.isVisibleAdd=!0},clickDet:function(t){var e=this;this.$confirm("此操作将永久删除, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(i["a"])(regeneratorRuntime.mark(function a(){var s,r;return regeneratorRuntime.wrap(function(a){while(1)switch(a.prev=a.next){case 0:return a.next=2,e.axios("/pvams/info/delete/".concat(t.id));case 2:s=a.sent,r=s.data.code,200===r&&(e.$message({type:"success",message:"操作成功"}),e.getTableList());case 5:case"end":return a.stop()}},a)})))}}},u=c,m=(a("4a7f"),a("2877")),f=Object(m["a"])(u,s,r,!1,null,"5b7335b1",null);e["default"]=f.exports}}]);
//# sourceMappingURL=imptMn.970b6d6d.js.map