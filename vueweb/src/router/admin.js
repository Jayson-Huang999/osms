import Vue from 'vue'
import Router from 'vue-router'

import store from '@/store';

import AdminLogin from '@/pages/admin/AdminLogin'
import Backstage from '@/pages/admin/Backstage'
import EditUser from '@/pages/admin/EditUser'
import EditSeller from '@/pages/admin/EditSeller'
import EditActivity from '@/pages/admin/EditActivity'
import EditAdmin from '@/pages/admin/EditAdmin'
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
            name: 'AdminLogin',
            component: AdminLogin
        }, {
            path: '/backstage',
            name: 'Backstage',
            redirect: "/backstage/editUser",
            component: Backstage,
            children: [
                {
                    path: 'editUser',
                    name: 'EditUser',
                    component: EditUser,
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
                }, {
                    path: 'editActivity',
                    name: 'EditActivity',
                    component: EditActivity,
                    meta: {
                        requireLogin: true,
                    },
                }, {
                    path: 'editAdmin',
                    name: 'EditAdmin',
                    component: EditAdmin,
                    meta: {
                        requireLogin: true,
                    },
                }
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
        if (store.state.adminToken) {
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
