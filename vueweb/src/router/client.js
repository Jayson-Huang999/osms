import Vue from 'vue'
import Router from 'vue-router'

import store from '@/store';

import Mall from '@/pages/client/Mall';
import MallShow from '@/pages/client/MallShow';
import MallIndex from '@/pages/client/MallIndex';
import MallLogin from '@/pages/client/MallLogin';
import GoodsDetail from '@/pages/client/GoodsDetail';
import KillGoodsDetail from '@/pages/client/KillGoodsDetail';
import GoodsList from '@/pages/client/GoodsList';
import Personal from '@/pages/client/Personal';
import MyOrder from '@/pages/client/MyOrder';
import MyData from '@/pages/client/MyData';
import Cart from '@/pages/client/Cart';
import ErrorPage from '@/pages/ErrorPage';
import favorite from '@/pages/client/favorite';
import myAddress from '@/pages/client/myAddress';
import Qs from 'qs'
import OrderConfirm from '@/pages/client/OrderConfirm'
import KillOrderConfirm from '@/pages/client/KillOrderConfirm'
import Payment from '@/pages/client/Payment'
/*
解决vue-router报NavigationDuplicated: Avoided redundant navigation to current location 的问题
避免到当前位置的冗余导航。 简单来说就是重复触发了同一个路由。
* */
Vue.prototype.qs = Qs
const original = Router.prototype.push
Router.prototype.push = function push(location) {
  return original.call(this, location).catch(err => err)
}


Vue.use(Router);

let router = new Router({
  routes: [
    {
      path:"/",
      redirect:"/mall"
    },{
      path: '/login',
      name: 'MallLogin',
      component: MallLogin
    },{
      path: '/mall',
      name: 'Mall',
      component: Mall,
      redirect:'/mall/show',
      children:[
        {
          path: 'show',
          name: 'MallShow',
          component: MallShow,
          redirect:'/mall/show/index',
          children:[
            {
              path: 'index',
              name: 'MallIndex',
              component: MallIndex
            },{
              path: 'goodsList/:categoryId/:keyword',
              name: 'GoodsList',
              component: GoodsList
            },
          ]
        },{
          path: 'goods/:id',
          name: 'GoodsDetail',
          component: GoodsDetail
        },{
          path: 'killGoods/:id',
          name: 'KillGoodsDetail',
          component: KillGoodsDetail
        }, {
          path: 'orderConfirm',
          name: 'OrderConfirm',
          component: OrderConfirm,
          meta: {
            requireLogin:true,
          },
        },
        {
          path: 'killOrderConfirm',
          name: 'KillOrderConfirm',
          component: KillOrderConfirm,
          meta: {
            requireLogin:true,
          },
        },
        {
          path: 'personal',
          name: 'Personal',
          component: Personal,
          redirect:'/mall/personal/cart',
          children:[
            {
              path: 'cart',
              name: 'Cart',
              component: Cart,
              meta: {
                requireLogin:true,
              },
            },{
              path: 'myData',
              name: 'MyData',
              component: MyData,
              meta: {
                requireLogin:true,
              },
            },{
              path: 'myOrder',
              name: 'MyOrder',
              component: MyOrder,
              meta: {
                requireLogin:true,
              },
            },
            {
              path: 'favorite',
              name: 'favorite',
              component: favorite,
              meta: {
                requireLogin:true,
              },
            },{
              path: 'myAddress',
              name: 'myAddress',
              component: myAddress,
              meta: {
                requireLogin:true,
              },
            }
          ]
        },{
          path: 'payment',
          name: 'Payment',
          component: Payment,
          meta: {
            requireLogin: true // 需要验证登录状态
          }
        },
      ]
    },{
  	  path:'*',
  	  name:'ErrorPage',
  	  component: ErrorPage
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
});

//登录拦截
router.beforeEach((to,from,next) => {
  if(to.meta.requireLogin){
    if(store.state.clientToken){
      next()
    }else{
      next({
        path: '/login',
        query:{redirect: to.fullPath}
      })
    }
  }else{
    next();
  }
});

export default router;
