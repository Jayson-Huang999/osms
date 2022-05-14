import Vue from 'vue'
import Router from 'vue-router'

import store from '@/store';

import SellerLogin from '@/pages/seller/SellerLogin';
import Backstage from '@/pages/seller/Backstage'
import Goods from '@/pages/seller/Goods'
import EditGoods from '@/pages/seller/EditGoods'
import Orders from '@/pages/seller/Orders'
import EditOrders from '@/pages/seller/EditOrders'
import Messages from '@/pages/seller/Messages'
import Activity from '@/pages/seller/Activity'
import EditSeller from '@/pages/seller/EditSeller'
import ErrorPage from '@/pages/ErrorPage'

/*
解决vue-router报NavigationDuplicated: Avoided redundant navigation to current location 的问题
避免到当前位置的冗余导航。 简单来说就是重复触发了同一个路由。
* */
// const original = Router.prototype.push
// Router.prototype.push = function push(location) {
//   return original.call(this, location).catch(err => err)
// }

Vue.use(Router)

let router = new Router({
    routes: [
        {
            path: "/",
            redirect: "/login"
        }, {
            path: '/login',
            name: 'SellerLogin',
            component: SellerLogin
        }, {
            path: '/backstage',
            name: 'Backstage',
            redirect: "/backstage/goods",
            component: Backstage,
            children: [
                {
                    path: 'goods',
                    name: 'Goods',
                    component: Goods,
                    meta: {
                        requireLogin: true,
                    },
                }, {
                    path: 'goods/:id',
                    name: 'EditGoods',
                    component: EditGoods,
                    meta: {
                        requireLogin: true,
                    },
                }, {
                    path: 'orders',
                    name: 'Orders',
                    component: Orders,
                    meta: {
                        requireLogin: true,
                    },
                }, {
                    path: 'orders/:id',
                    name: 'EditOrders',
                    component: EditOrders,
                    meta: {
                        requireLogin: true,
                    },
                }, {
                    path: 'messages',
                    name: 'Messages',
                    component: Messages,
                    meta: {
                        requireLogin: true,
                    },
                }, {
                    path: 'activity/:id',
                    name: 'Activity',
                    component: Activity,
                    meta: {
                        requireLogin: true,
                    },
                }, {
                    path: 'editSeller',
                    name: 'EditSeller',
                    component: EditSeller,
                    meta: {
                        requireLogin: true,
                    },
                },
            ]
        }, {//404页面
            path: '*',
            name: 'ErrorPage',
            component: ErrorPage
        }
    ],
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    }
});

//登录拦截
router.beforeEach((to, from, next) => {
    if (to.meta.requireLogin) {
        if (store.state.sellerToken) {
            next()
        } else {
            next({
                path: '/login',
                query: {redirect: to.fullPath}
            })
        }
    } else {
        next();
    }
});

export default router;
