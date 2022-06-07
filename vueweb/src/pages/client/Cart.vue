<template>
    <div class="Cart">
        <div v-if="orderList.length>0">
            <div class="cartTableHeader">
                <span>商品信息</span>
                <span>单价</span>
                <span>数量</span>
                <span>小计</span>
                <span>交易操作</span>
            </div>
            <div class="cartTableHeader">
                <input type="checkbox" id="selectall" style="width: 20px;
          margin-top:10px;
          margin-left:20px;
          height: 20px;
          display: inline-block;" @click="operateAll($event)">
                <span style="display: inline-block;margin-left:5px;position:relative;bottom:3px;font-weight:bold">全选</span>
            </div>
            <ul class="orderList">
                <li v-for="(item,index) in orderList" :key="'cart'+item.cart_id">
                    <div class="orderDetail">
                        <input type="checkbox" class="selectCart" :value="index" @click="operate($event)">
                        <img :src="item.url" alt="商品图片"/>
                        <div class="goodsName">
                            <p @click="navTo('/mall/goods/'+item.product_id)">{{item.product_name}}</p>
                            <span>{{item.specification_name}} {{item.size}}</span>
                            <!--<span>{{item.size}}</span>-->
                        </div>
                        <span class="unitPrice" style="color:#be4141" v-if="item.activity">{{'￥'+item.price+'(折后价'+parseFloat(item.price*item.preferentiallevel/100).toFixed(2)+')'}}</span>
                        <span class="unitPrice" v-else>{{'￥'+item.price}}</span>
                        <span class="num">
              <NumberInput
                      @changeHandle="numberChange(item.cart_id)"
                      :initNum="item.quantity"
                      v-model="item.temGoodsNum"
                      :min="1"
                      :max="999"
              />
            </span>
                        <!-- <input @change="numberChange(item.id)" type="text" v-model="item.temGoodsNum" min="1" class="numInput" /> -->
                        <span class="amount">{{'￥'+parseFloat(item.amount*item.preferentiallevel/100).toFixed(2)}}</span>
                        <!--<button>？？？</button>-->
                    </div>
                </li>
            </ul>
            <div class="cartFooter">
                <span>应付金额：</span>
                <span class="total" v-model="totalAmount">{{'￥'+totalAmount}}</span>
                <el-button type="text" class="delete" @click="removeFromCart">删除</el-button>
                <!--<button class="delete"@click="removeFromCart">删除</button>-->
                <el-button type="text" class="submit" @click="confirmOrder">下单</el-button>
                <!--<button class="submit" @click="generateOrders">下单</button>-->
            </div>
        </div>
        <p class="emptyTips" v-else>购物车还是空滴~</p>
    </div>
</template>

<script>
    import {mapState} from 'vuex';
    import {getCarts, removeFromCart, generateOrders, updateCart,getActivityProducts} from '../../api/client';
    import NumberInput from '../../components/NumberInput';
    import OrderConfirm from './OrderConfirm';
    import {mapMutations} from 'vuex'

    export default {
        name: 'Cart',
        components: {
            NumberInput
        },
        computed: {
            ...mapState([
                'clientToken',
                'clientId',
                'ordersToConfirm',
                'activityProducts'
            ])
            // totalAmount(){
            //   let amount = 0;
            //   if(this.checkout()){
            //     var
            //   }
            //   else
            //     amount=0
            //   return amount
            //   this.orderList.map((item,index)=>{
            //     amount+=item.amount;
            //   })
            //   return amount;
            // }
        },
        data() {
            return {
                orderList: [],
                totalAmount: 0,
                timer:null
            }
        },

        methods: {
            ...mapMutations({
                setOrdersToConfirm: "SET_ORDERS_TO_CONFIRM",
                setActivityProducts: "SET_ACTIVITY_PRODUCTS",
            }),
            operate() {
                var selectall = document.getElementById("selectall");
                var selectedList = document.getElementsByClassName("selectCart");
                this.totalAmount = this.getTotalAmount(selectedList);
                if (selectall.checked == true) {
                    if (event.target.checked == false)
                        selectall.checked = false;
                } else {
                    for (var i = 0; i < selectedList.length; i++) {
                        if (!selectedList[i].checked)
                            return
                    }
                    selectall.checked = true;
                }
            },
            operateAll() {
                var selectedList = document.getElementsByClassName("selectCart");
                var result = event.target.checked;

                for (var i = 0; i < selectedList.length; i++) {
                    selectedList[i].checked = result;
                }
                this.totalAmount = this.getTotalAmount(selectedList);
            },
            getTotalAmount(selectedList) {
                var tempTotalAmount = 0;
                for (var i = 0; i < selectedList.length; i++) {
                    if (selectedList[i].checked) {
                        tempTotalAmount += (this.orderList[selectedList[i].value].quantity * this.orderList[selectedList[i].value].price*this.orderList[selectedList[i].value].preferentiallevel/100);
                    }
                }
                return tempTotalAmount.toFixed(2);
            },
            getCarts() {
                const res = getCarts(this.clientId,this.clientToken);
                res
                    .then((data) => {
                        this.orderList = data;
                        console.log(this.orderList);
                        this.orderList.map((item, index) => {
                            item.temGoodsNum = item.quanity;
                        })
                            getActivityProducts().then((data0) => {
                                console.log(data0);
                                var tempList = [];
                                for (var i = 0; i < data0.length; i++) {
                                    var starttime = new Date(data0[i].starttime.replace(/-/g, '/'));
                                    var endtime = new Date(data0[i].endtime.replace(/-/g, '/'));
                                    // console.log(starttime.getTime());
                                    // console.log(endtime.getTime());
                                    if (new Date().getTime() > starttime.getTime() && new Date().getTime() < endtime.getTime()&&data0[i].remain>0) {
                                        tempList.push(data0[i]);
                                    }
                                }
                                this.setActivityProducts(tempList);
                                for (var i = 0; i < this.orderList.length; i++) {
                                    for (var j = 0; j < this.activityProducts.length; j++) {
                                        if (this.orderList[i].product_id == this.activityProducts[j].product_id)
                                            if (new Date(this.activityProducts[j].starttime.replace(/-/g, '/')).getTime() <= new Date().getTime() && new Date(this.activityProducts[j].endtime.replace(/-/g, '/')).getTime() > new Date().getTime()) {
                                                this.orderList[i].preferentiallevel = this.activityProducts[j].preferentiallevel;
                                                this.orderList[i].activity = true;
                                                var temp = this.orderList[i];
                                                this.$set(this.orderList, i, temp);
                                                break;
                                            }
                                    }
                                    if (j == this.activityProducts.length) {
                                        this.orderList[i].preferentiallevel = 100;
                                        this.orderList[i].activity = false;
                                        var temp = this.orderList[i];
                                        this.$set(this.orderList, i, temp);
                                    }
                                }
                            })
                        })
                        .catch((e) => {
                            alert(e);
                    })
                    .catch((e) => {
                        alert(e);
                    })
            },
            numberChange(cartId) {
                this.orderList.map((item, index) => {
                    if (cartId === item.cart_id) {
                        item.amount = item.temGoodsNum * item.price;
                        item.quantity = item.temGoodsNum;
                        var selectedList = document.getElementsByClassName("selectCart");
                        this.totalAmount = this.getTotalAmount(selectedList);
                        const res = updateCart({
                            "id": item.cart_id,
                            "userId": null,
                            "specificationId": null,
                            "quantity": item.temGoodsNum
                        });
                        res.catch((e) => {
                            alert(e);
                        })
                    }
                })
            },
            checkout() {
                var selectedList = document.getElementsByClassName("selectCart");
                for (var i = 0; i < selectedList.length; i++) {
                    if (selectedList[i].checked)
                        return true;
                }
                return false;
            },
            removeFromCart() {
                if (this.checkout()) {

                    this.$confirm('确认删除这些商品吗？', "提示", {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        var idList = [];
                        var tempOrdeList = [];
                        var selectedList = document.getElementsByClassName("selectCart");
                        for (var i = 0; i < selectedList.length; i++) {
                            if (selectedList[i].checked)
                                idList.push(this.orderList[selectedList[i].value].cart_id);
                            else
                                tempOrdeList.push(this.orderList[selectedList[i].value]);
                        }
                        console.log(idList);
                        const res = removeFromCart({"data": idList});
                        res
                            .then(() => {
                                // alert('删除购物车商品成功！');
                                this.$message({
                                    type: 'success',
                                    message: '删除成功!'
                                });
                                // this.orderList.map((item,index)=>{
                                //     if(item.cart_id===cart_id){
                                //         this.orderList.splice(index,1);
                                //     }
                                // })
                                this.orderList = tempOrdeList;
                            })
                            .catch((e) => {
                                alert(e);
                            });
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                } else {
                    this.$alert('你还没有选中任何商品呢~', '提示', {
                        confirmButtonText: '确定',
                    });
                }

            },
            navTo(route) {
                this.$router.push(route);
            },
            confirmOrder() {
                if (this.checkout()) {
                    var flag=true;
                    var selectedList = document.getElementsByClassName("selectCart");
                    var finalList = [];
                    var tempOrderList = [];
                    for (var i = 0; i < selectedList.length; i++) {
                        if (selectedList[i].checked) {
                            finalList.push(this.orderList[selectedList[i].value]);
                            console.log(i);
                        } else
                            tempOrderList.push(this.orderList[selectedList[i].value]);
                    }
                    let cartList = [];
                    let idList = [];
                    finalList.map((item, index) => {
                        idList.push(item.cart_id);
                        if(item.preferentiallevel>0&&item.preferentiallevel<100&&item.quantity>1){
                            alert("限时购商品一次只能购买一件");
                            flag=false;
                        }
                        cartList.push({
                            product_name: item.product_name,
                            size: item.size,
                            specification_name: item.specification_name,
                            specification_id: item.specification_id,
                            price: item.price,
                            num: item.quantity,
                            img: item.url,
                            preferentiallevel: item.preferentiallevel
                        })
                    });
                    if(!flag){
                        return;
                    }
                    this.setOrdersToConfirm(JSON.stringify(cartList));
                    this.$router.push({
                        name: 'OrderConfirm',
                        query: {data:JSON.stringify(idList)}
                    })
                } else {
                    this.$alert('你还没有选中任何商品呢~', '提示', {
                        confirmButtonText: '确定',
                    });
                }
            },
            // generateOrders(){
            //   if(this.checkout()){
            //       var selectedList=document.getElementsByClassName("selectCart");
            //       var finalList=[];
            //       var tempOrderList=[];
            //       for(var i=0;i<selectedList.length;i++){
            //           if(selectedList[i].checked) {
            //               finalList.push(this.orderList[selectedList[i].value]);
            //               console.log(i);
            //           }
            //           else
            //               tempOrderList.push(this.orderList[selectedList[i].value]);
            //       }
            //       let cartList = [];
            //       let idList=[];
            //       finalList.map((item,index)=>{
            //           console.log(item.temGoodsNum);
            //           idList.push(item.cart_id);
            //           cartList.push({
            //               id:null,
            //               addressId:null,
            //               specificationId:item.specification_id,
            //               userId:this.clientId,
            //               status:1,
            //               quantity:item.quantity,
            //               preferentialprice:0,
            //               totalprice:item.amount,
            //               createtime:null
            //           })
            //       });
            //       const res = generateOrders(cartList);
            //       res
            //           .then(()=>{
            //             console.log(idList);
            //               alert('下单成功！');
            //               this.orderList=tempOrderList;
            //               // const res = removeFromCart({"data":idList});
            //               //this.orderList = [];
            //               const res = removeFromCart({"data":idList});
            //           })
            //           .catch((e)=>{
            //               alert(e);
            //           })
            //   }
            //   else{
            //       this.$alert('你还没有选中任何商品呢~', '提示', {
            //           confirmButtonText: '确定',
            //       });
            //   }
            //
            // }
        },

        beforeDestroy() {
            clearInterval(this.timer);
            this.timer = null;
        },

        mounted() {
            this.getCarts();
            this.timer=setInterval(()=>{
                getActivityProducts().then((data0) => {
                    var tempList = [];
                    for (var i = 0; i < data0.length; i++) {
                        var starttime = new Date(data0[i].starttime.replace(/-/g, '/'));
                        var endtime = new Date(data0[i].endtime.replace(/-/g, '/'));
                        // console.log(starttime.getTime());
                        // console.log(endtime.getTime());
                        if (new Date().getTime() > starttime.getTime() && new Date().getTime() < endtime.getTime()&&data0[i].remain>0) {
                            tempList.push(data0[i]);
                        }
                    }
                    this.setActivityProducts(tempList);
                    for (var i = 0; i < this.orderList.length; i++) {
                        for (var j = 0; j < this.activityProducts.length; j++) {
                            if (this.orderList[i].product_id == this.activityProducts[j].product_id)
                                if (new Date(this.activityProducts[j].starttime.replace(/-/g, '/')).getTime() <= new Date().getTime() && new Date(this.activityProducts[j].endtime.replace(/-/g, '/')).getTime() > new Date().getTime()) {
                                    this.orderList[i].preferentiallevel = this.activityProducts[j].preferentiallevel;
                                    this.orderList[i].activity = true;
                                    var temp = this.orderList[i];
                                    this.$set(this.orderList, i, temp);
                                    break;
                                }
                        }
                        if (j == this.activityProducts.length) {
                            this.orderList[i].preferentiallevel = 100;
                            this.orderList[i].activity = false;
                            var temp = this.orderList[i];
                            this.$set(this.orderList, i, temp);
                        }
                    }
                }).catch((e) => {
                    alert(e);
                })
            },2000)
        },
    }
</script>

<style scoped lang="less">
    @import "../../assets/css/var.less";

    .Cart {
        width: 100%;

        .emptyTips {
            width: 200px;
            text-align: center;
            display: block;
            margin: 30px auto;
            color: @thirdColor;
            font-size: 20px;
        }

        .cartTableHeader {
            width: 100%;
            height: 40px;
            background-color: #f5f5f5;
            border: 1px solid @borderColor;
            color: @fontDefaultColor;
            font-size: 14px;
            line-height: 40px;

            span {
                display: inline-block;
                width: 14%;

                &:first-child {
                    width: 40%;
                    text-align: center;
                }
            }
        }

        .orderList {
            width: 100%;

            li {
                border: 1px solid @borderColor;
                border-top: none;
                font-size: 13px;

                .orderHeader {
                    background-color: #f1f1f1;
                    height: 40px;
                    line-height: 40px;
                    padding: 0 5px;

                    .orderTime {
                        font-weight: 600;
                    }

                    .orderId, .state {
                        margin-left: 10px;
                    }
                }

                .orderDetail {
                    width: 100%;
                    padding: 10px;
                    position: relative;
                    overflow: hidden;

                    img {
                        width: 84px;
                        height: 84px;
                        display: inline-block;
                    }

                    input {
                        height: 84px;
                        width: 20px;
                        margin-right: 10px;
                        margin-left: 10px;
                        display: inline-block;
                    }

                    .goodsName {
                        display: inline-block;
                        margin-left: 5px;
                        width: 190px;
                        vertical-align: top;

                        p {
                            cursor: pointer;
                            line-height: 20px;

                            &:hover {
                                text-decoration: underline;
                            }
                        }

                        span {
                            color: @fontDefaultColor;
                            display: block;
                            margin-top: 10px;
                        }
                    }

                    .unitPrice, .num, .amount {
                        display: inline-block;
                        vertical-align: top;
                        width: 14.5%;
                        height: 85px;
                        line-height: 85px;
                        text-align: center;
                    }

                    .NumberInput {
                        position: relative;
                        top: 25px;
                    }

                    button {
                        position: absolute;
                        right: 90px;
                        bottom: 40px;
                        width: 70px;
                        height: 30px;
                        border-radius: 3px;
                        background-color: @falseColor;
                        color: white;
                        border: none;

                    }
                }
            }
        }

        .cartFooter {
            width: 100%;
            height: 60px;
            line-height: 60px;
            padding: 0 10px;
            border: 1px solid @borderColor;
            border-top: none;
            background-color: #f5f5f5;
            position: relative;

            span {
                color: @fontDefaultColor;
                display: inline-block;
                vertical-align: top;
            }

            .total {
                color: @falseColor;
                font-size: 25px;
                font-weight: 600;
            }

            .submit {
                position: absolute;
                right: 0;
                top: 0;
                width: 100px;
                height: 100%;
                background-color: @thirdColor;
                border: none;
                color: white;
                font-size: 20px;
            }

            .delete {
                position: absolute;
                right: 100px;
                top: 0;
                width: 100px;
                height: 100%;
                background-color: @falseColor;
                color: white;
                border: none;
                font-size: 20px;
            }
        }
    }
</style>