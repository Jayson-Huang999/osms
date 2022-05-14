import 'babel-polyfill';
import Vue from 'vue';
import App from './App';
import router from './router/client';
import store from './store';


import './assets/css/common.less';
import './assets/font/iconfont.css';

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)
import Global from './util/Global'
Vue.use(Global)
import vRegion from 'v-region'
Vue.use(vRegion)

window.router=router


Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
});
