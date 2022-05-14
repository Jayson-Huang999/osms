import axios from '../config/axios-admin';

//管理员登录
export function login(data) {
    const res = axios.post('/api/admins/login', data);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                console.log(result.data)
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.data);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            console.log("失败")
            reject(e.toString())
        })
    })
}

//获取所有用户信息
export function getAllUser() {
    const res = axios.get('/api/user/getAllUser');
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.data);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}

//搜索用户
export function getSearchUser(val) {
    const res = axios.get('/api/user/searchUserByKeyWord?keyword=' + val);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.data);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}

//删除指定用户
export function deleteUser(id) {
    const res = axios.delete('/api/user/deleteByID?id=' + id);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve();
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}

//获取所有店铺信息
export function getAllSellersByStatus(status) {
    const res = axios.get('/api/seller/getAllSellersByStatus?status=' + status);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.data);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}

//搜索店铺
export function getSearchSeller(data) {
    const res = axios.post('/api/seller/searchSellerByKeyWordAndStatus', data);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.data);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}

//改变商家账户的状态
export function changeSellerByID(data) {
    const res = axios.post('/api/seller/changeSellerByID?id=',data);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve();
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString());
        })
    })
}


//更改个人信息
export function saveChange(data) {
    const res = axios.post('/api/admins/saveChange', data);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.message);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}


//获取所有活动信息
export function getActivityByStatus(status) {
    const res = axios.get('/api/activity/getActivityByStatus?status=' + status);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.data);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}

//搜索活动
export function getSearchActivity(data) {
    const res = axios.post('/api/activity/getSearchActivity', data);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve(json.data);
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString())
        })
    })
}

//改变活动的发布的状态
export function changeActivityByID(data) {
    const res = axios.post('/api/activity/changeActivityByID?id=',data);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status)
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve();
            } else {
                reject(json.message);
            }
        }).catch((e) => {
            reject(e.toString());
        })
    })
}
