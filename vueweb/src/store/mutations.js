import * as types from './mutation-types';
import {setLocalItem} from '../util/util';

const mutations = {
  // 管理员
  [types.SET_ADMIN_ID]: (state, adminId) => {
    state.adminId = adminId;
    setLocalItem('adminId', adminId);
  },
  [types.SET_ADMIN_NAME]: (state, adminName) => {
    state.adminName = adminName;
    setLocalItem('adminName', adminName);
  },
  [types.SET_ADMIN_TOKEN]: (state, adminToken) => {
    state.adminToken = adminToken;
    setLocalItem('adminToken', adminToken);
  },
  [types.ADMIN_LOGOUT]: (state) => {
    state.adminId='';
    state.adminName = '';
    state.adminToken = null;
    localStorage.removeItem('adminId');
    localStorage.removeItem('adminName');
    localStorage.removeItem('adminToken');
  },
  // 商家
  [types.SET_SELLER_ID]: (state, sellerId) => {
    state.sellerId = sellerId;
    setLocalItem('sellerId', sellerId);
  },
  [types.SET_SELLER_NAME]: (state, sellerName) => {
    state.sellerName = sellerName;
    setLocalItem('sellerName', sellerName);
  },
  [types.SET_SELLER_TOKEN]: (state, sellerToken) => {
    state.sellerToken = sellerToken;
    setLocalItem('sellerToken', sellerToken);
  },
  [types.SELLER_LOGOUT]: (state) => {
    state.sellerId = '';
    state.sellerName = '';
    state.sellerToken = null;
    localStorage.removeItem('sellerId');
    localStorage.removeItem('sellerName')
    localStorage.removeItem('sellerToken')
  },

  //客户
  [types.SET_CLIENT_ID]: (state, clientId) => {
    state.clientId = clientId;
    setLocalItem('clientId', clientId);
  },
  [types.SET_CLIENT_NAME]: (state, clientName) => {
    state.clientName = clientName;
    setLocalItem('clientName', clientName);
  },
  [types.SET_CLIENT_TOKEN]: (state, clientToken) => {
    state.clientToken = clientToken;
    setLocalItem('clientToken', clientToken);
  },
  [types.CLIENT_LOGOUT]: (state) => {
    state.clientId='';
    state.clientName = '';
    state.clientToken = null;
    localStorage.removeItem('clientId');
    localStorage.removeItem('clientName');
    localStorage.removeItem('clientToken');
  },

  [types.SET_ORDERS_TO_CONFIRM]:(state, orderList) => {
    state.ordersToConfirm = orderList;
    setLocalItem('ordersToConfirm',orderList);
  },
  [types.SET_ACTIVITY_PRODUCTS]:(state, activityProducts) => {
    state.activityProducts = activityProducts;
    setLocalItem('activityProducts',activityProducts);
  },
}
export default mutations;
