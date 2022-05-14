import {getLocalItem} from '../util/util';
const state = {
  //管理员后台管理
  adminId:getLocalItem('adminId')?getLocalItem('adminId'):'',
  adminName:getLocalItem('adminName')?getLocalItem('adminName'):'',
  adminToken:getLocalItem('adminToken')?getLocalItem('adminToken'):null,
  //商家后台管理
  sellerId:getLocalItem('sellerId')?getLocalItem('sellerId'):'',
  sellerName:getLocalItem('sellerName')?getLocalItem('sellerName'):'',
  sellerToken:getLocalItem('sellerToken')?getLocalItem('sellerToken'):null,
  //客户商城
  clientId:getLocalItem('clientId')?getLocalItem('clientId'):'',
  clientName:getLocalItem('clientName')?getLocalItem('clientName'):'',
  clientToken:getLocalItem('clientToken')?getLocalItem('clientToken'):null,

  ordersToConfirm:getLocalItem('ordersToConfirm')?getLocalItem('ordersToConfirm'):null,
  activityProducts:getLocalItem('activityProducts')?getLocalItem('activityProducts'):null,
}

export default state;
