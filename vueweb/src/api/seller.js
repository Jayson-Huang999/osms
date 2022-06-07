import axios from '../config/axios-seller';
import {data} from "autoprefixer";

//商家登录
export function login(data) {
    const res = axios.post('/api/seller/login', data);
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
            reject(e.toString())
        })
    })
}

//商家入驻申请
export function signup(data) {
    const res = axios.post('/api/seller/signup', data);
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


//修改店铺信息
export function saveChange(data) {
    const res = axios.post('/api/seller/saveChange', data);
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

//通过商家的sid得到所有未回复的信息
export function getNoReplyMsg(sid) {
    const res = axios.get('/api/messages/getNoReplyMsgBySid?id=' + sid);
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

//得到已回复的信息
export function getRepliedMsg(id) {
    const res = axios.get('/api/messages/getRepliedMsgBySid?id=' + id);
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

//回复用户信息
export function reply(data) {
    const res = axios.post('/api/replies/replyBySeller', data);
    return new Promise((resolve, reject) => {
        res
            .then((result) => {
                if (result.status === 200) {
                    return result.data;
                } else {
                    reject(result.status)
                }
            })
            .then((json) => {
                if (json.code === 0) {
                    resolve();
                } else {
                    reject(json.message);
                }
            })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//得到订单
export function getOrders(data) {
    const res = axios.post('/api/orders/selectOrdersBySellerIdAndStatus', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//得到单个订单详情
export function getAOrder(id) {
    const res = axios.get('/api/orders/getOrdersDetail?order_id=' + id);
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

//修改订单
export function changeOrder(data) {
    const res = axios.post('/api/orders/changeOrder', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//得到商品
export function getAllProductByCidAndSid(data) {
    const res = axios.post('/api/product/getAllProductByCidAndSid', data);
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

//得到类目
export function getAllBrands() {
    const res = axios.get('/api/brand/getAllBrands');
    return new Promise((resolve, reject) => {
        res
            .then((result) => {
                if (result.status === 200) {
                    return result.data;
                } else {
                    reject(result.status)
                }
            })
            .then((json) => {
                if (json.code === 0) {
                    resolve(json.data);
                } else {
                    reject(json.message);
                }
            })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//得到所有类目
export function getAllCategories() {
    const res = axios.get('/api/category/getAllCategories');
    return new Promise((resolve, reject) => {
        res
            .then((result) => {
                if (result.status === 200) {
                    return result.data;
                } else {
                    reject(result.status)
                }
            })
            .then((json) => {
                if (json.code === 0) {
                    resolve(json.data);
                } else {
                    reject(json.message);
                }
            })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//增加类目
export function addCategory(data) {
    const res = axios.post('/api/category/addCategory', data);
    return new Promise((resolve, reject) => {
        res
            .then((result) => {
                if (result.status === 200) {
                    return result.data;
                } else {
                    reject(result.status)
                }
            })
            .then((json) => {
                if (json.code === 0) {
                    resolve();
                } else {
                    reject(json.message);
                }
            })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//添加品牌
export function addBrand(data) {
    const res = axios.post('/api/brand/addBrand', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//得到商品信息
export function getGoodsInfo(id) {
    const res = axios.get('/api/product/getProductInfoByID?id=' + id);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//增加商品
export function addProduct(data) {
    const res = axios.post('/api/product/addProduct', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//添加商品图片
export function addImage(data) {
    const res = axios.post('/api/image/addImageByProductID', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//增加规格
export function addSpec(data) {
    const res = axios.post('/api/specification/addSpecByProductID', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//删除规格
export function deleteSpec(id) {
    const res = axios.delete('/api/specification/deleteSpecByID?id=' + id);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status);
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve();
            } else {
                reject(json.message);
            }
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//删除图片
export function deleteImage(id) {
    const res = axios.delete('/api/image/deleteImageByID?id=' + id);
    return new Promise((resolve, reject) => {
        res.then((result) => {
            if (result.status === 200) {
                return result.data;
            } else {
                reject(result.status);
            }
        }).then((json) => {
            if (json.code === 0) {
                resolve();
            } else {
                reject(json.message);
            }
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//更新商品信息
export function updateSpec(data) {
    const res = axios.post('/api/specification/updateSpec', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//更新商品信息
export function updateProduct(data) {
    console.log("lgl", data);
    const res = axios.post('/api/product/updateProduct', data);
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
        })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//删除指定商品
export function deleteGoods(id) {
    const res = axios.delete('/api/product/deleteByID?id=' + id);
    return new Promise((resolve, reject) => {
        res
            .then((result) => {
                if (result.status === 200) {
                    return result.data;
                } else {
                    reject(result.status)
                }
            })
            .then((json) => {
                if (json.code === 0) {
                    resolve();
                } else {
                    reject(json.message);
                }
            })
            .catch((e) => {
                reject(e.toString())
            })
    })
}

//添加活动
export function addActivity(data) {
    console.log(data);
    const res = axios.post('/api/activity/addActivity', data);
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


export function getTotalStockByPid(pid) {
    const res = axios.get('/api/specification/getTotalStockByPid?id=' + pid);
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