import axios from '../config/axios-client';

//用户登录
export function login(data) {
  const res = axios.post('/api/user/login', data);
  return new Promise((resolve, reject) => {
    res.then((result) => {
      if (result.status === 200) {
        console.log(result.data);
        return result.data;
      } else {
        console.log(result.data);
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

//用户注册
export function signup(data) {
  const res = axios.post('/api/user/signup', data);
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
export function getTypes() {
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

//获得不同类目的商品
export function getGoodsList(categoryId) {
  const res = axios.get('/api/product/getProductInfoByCategoryID?id=' + categoryId);
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
//获得所有活动商品
export function getActivityProducts() {
  const res = axios.get('/api/activity/getActivityProducts');
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
//判断是否收藏该商品
export function hasStored(id,pid,token) {
  const res = axios.get('/api/product/hasStored?userid=' + id+'&pid='+pid+"&token="+token);
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

//获得商品详情页信息
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

//获得商品详情页问答区数据
export function getGoodsMsg(gid) {
  const res = axios.get('/api/messages/getGoodsMsg?gid=' + gid);
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

//发送用户对商品的提问msg
export function askGoodsMsg(data) {
  const res = axios.post('/api/messages/askProductMsg', data);
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

//加入购物车
export function addToCart(data) {
  const res = axios.post('/api/cartProduct/addToCart', data);
  return new Promise((resolve, reject) => {
    res
      .then((result) => {
        console.log(result);
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

//更新购物车商品数量
export function updateCart(data) {
  const res = axios.put('/api/cartProduct/updateCart', data);
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
//下单后更新库存
export function updateSpecificationStock(data) {
  const res = axios.put('/api/specification/updateSpecificationStock',data);
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
//下单后更新活动商品的剩余量
export function updateActivityRemain(data) {
  const res = axios.put('/api/activity/updateActivityRemain',data);
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
//商品详情页更新不同规格的库存
export function getProductStock(id) {
  const res = axios.get('/api/product/getProductStock?id='+id);
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

//获得用户购物车中内容
  export function getCarts(id,token) {
    const res = axios.get('/api/cartProduct/getCartProducts?user_id=' + id+"&token="+token);
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


//获得用户订单列表
export function getOrderByStatus(id, status,token) {
  const res = axios.get('/api/orders/getOrders?user_id=' + id + "&status=" + status+"&token="+token);
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

// //获得用户订单列表
// export function getOrderByState(state,token) {
//   console.log(state);
//   console.log(token);
//   const res = axios.get('/api/orders/getOrders?user_id=' + id);
//   return new Promise((resolve, reject) => {
//     res
//         .then((result) => {
//           if (result.status === 200) {
//             return result.data;
//           } else {
//             reject(result.status)
//           }
//         })
//         .then((json) => {
//           if (json.code === 0) {
//             resolve(json.data);
//           } else {
//             reject(json.message);
//           }
//         })
//         .catch((e) => {
//           reject(e.toString())
//         })
//   })
// }
//获取用户地址
export function getAddresses(id,token) {
  const res = axios.get('/api/address/getAddresses?user_id='+id+"&token="+token);
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
//用户添加收货地址
export function addAddress(data) {
  const res = axios.post('/api/address/addAddress',data);
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
//从购物车中移除商品
export function removeFromCart(data) {
  const res = axios.delete('/api/cartProduct/removeFromCart', data);
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


// //确认收货
// export function confirmReceive(id) {
//   const res = axios.get('/api/mall/confirmReceive?id=' + id);
//   return new Promise((resolve, reject) => {
//     res
//       .then((result) => {
//         if (result.status === 200) {
//           return result.data;
//         } else {
//           reject(result.status)
//         }
//       })
//       .then((json) => {
//         if (json.code === 0) {
//           resolve();
//         } else {
//           reject(json.message);
//         }
//       })
//       .catch((e) => {
//         reject(e.toString())
//       })
//   })
// }

//更新订单状态
export function updateOrdersStatus(data) {
  const res = axios.put('/api/orders/updateOrdersStatus', data);
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

export function updateOrdersStatusByids(data) {
  const res = axios.put('/api/orders/updateOrdersStatusByids', data);
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
//获得用户资料
export function getUserData(id) {
  const res = axios.get('/api/user/' + id);
  return new Promise((resolve, reject) => {
    res
        .then((result) => {
          if (result.status === 200) {
            return result.data;0
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

//更改用户资料
export function updateUserData(data) {
  const res = axios.post('/api/user/updateUserData', data);
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

//更改邮箱
export function updateEmail (data) {
  const res = axios.post('/api/user/update/email', data);
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
//更改手机号
export function updatePhone (data) {
  const res = axios.post('/api/user/update/phone', data);
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

//更改用户密码
export function updatePwd(data) {
  const res = axios.post('/api/user/updatePwd', data);
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

//结算购物车
export function generateOrders(data) {
  const res = axios.post('/api/orders/generateOrders', data);
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

//发送评价
export function sendComment(data) {
  const res = axios.post('/api/comments/sendComment', data);
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

//获得商品评论
export function getComment(id) {
  const res = axios.get('/api/comments/getGoodsComment?id=' + id);
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

//关键词搜索商品
export function searchGoods(keyword = '') {
  const res = axios.get('/api/product/searchProductByKeyWord?keyword=' + keyword);
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
//查询收藏
export function getFavoriteProducts(data) {
  const res = axios.get('/api/favorite/getFavoriteProducts', { params: { id:data.clientId,start:data.start, limit:data.limit } });
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

//删除收藏
export function deleteFavorites(favorite_id) {
  const res = axios.delete('/api/favorite/removeFromFavorite?favorite_id='+favorite_id);
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


//查询用户地址
export function showAddresses(data) {
  const res = axios.post('/api/address/getAlladdress',data);
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
//更新用户积分
export function updateUserScore(id,score) {
  const res = axios.put('/api/user/updateUserScore?user_id='+id+"&score="+score);
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
// //提交新地址
// export function addAddress(data) {
//   const res = axios.post('/api/address/addAddress',data);
//   return new Promise((resolve, reject) => {
//     res
//         .then((result) => {
//           if (result.status === 200) {
//             return result.data;
//           } else {
//             reject(result.status)
//           }
//         })
//         .then((json) => {
//           if (json.code === 0) {
//             resolve(json.data);
//           } else {
//             reject(json.message);
//           }
//         })
//         .catch((e) => {
//           reject(e.toString())
//         })
//   })
// }

//更新地址
export function updateAddress(data) {
  const res = axios.post('/api/address/updateAddress',data);
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
//删除地址
export function deleteAddress(data) {
  const res = axios.delete('/api/address/'+data);
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
//获取人气商品
export function getfavoriteGoodsList() {
  const res = axios.get('/api/product/getfavoriteGoodsList/');
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
//获取新品商品
export function getNewGoodsList() {
  const res = axios.get('/api/product/getNewGoodsList/');
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
export function getBrandByCid(id) {
  const res = axios.get('/api/brand/getBrandByCid/?id='+id);
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
//获得不同品牌的商品
export function getGoodsListBybid(brandId) {
  const res = axios.get('/api/product/getGoodsListBybid?id=' + brandId);
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
//根据orderid获取order
export function getOrderByoid(order_id) {
  const res = axios.get('/api/orders/getOrdersDetail?order_id=' + order_id);
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