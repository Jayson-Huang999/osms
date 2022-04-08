/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/3/11 15:15:18                           */
/*==============================================================*/


drop table if exists osms_activity;

drop table if exists osms_address;

drop table if exists osms_admins;

drop table if exists osms_brand;

drop table if exists osms_cart_product;

drop table if exists osms_category;

drop table if exists osms_comments;

drop table if exists osms_dict;

drop table if exists osms_favorite;

drop table if exists osms_image;

drop table if exists osms_message;

drop table if exists osms_order;

drop table if exists osms_payinfo;

drop table if exists osms_preferentiallevel;

drop table if exists osms_product;

drop table if exists osms_replies;

drop table if exists osms_seller;

drop table if exists osms_specification;

drop table if exists osms_user;

/*==============================================================*/
/* Table: osms_activity                                         */
/*==============================================================*/
create table osms_activity
(
   id                   bigint not null auto_increment,
   product_id           bigint,
   preferentiallevel_id bigint,
   starttime            datetime,
   endtime              datetime,
   quantity             int,
   primary key (id)
);

alter table osms_activity comment '活动表';

/*==============================================================*/
/* Table: osms_address                                          */
/*==============================================================*/
create table osms_address
(
   id                   bigint not null,
   user_id              bigint,
   receivername         varchar(30),
   receiverphone        varchar(30),
   receiverprovince     varchar(30),
   receivercity         varchar(30),
   receiverdistrict     varchar(30),
   receiveraddress      varchar(255),
   is_default           bool,
   primary key (id)
);

alter table osms_address comment '收货地址信息表';

/*==============================================================*/
/* Table: osms_admins                                           */
/*==============================================================*/
create table osms_admins
(
   id                   bigint not null auto_increment,
   name                 varchar(30),
   account              varchar(50),
   password             varchar(50),
   lastlogintime        varchar(50),
   primary key (id)
);

alter table osms_admins comment '后台管理';

/*==============================================================*/
/* Table: osms_brand                                            */
/*==============================================================*/
create table osms_brand
(
   id                   bigint not null auto_increment,
   name                 varchar(30),
   logourl              varchar(255),
   describ              varchar(500),
   primary key (id)
);

alter table osms_brand comment '商品品牌信息表';

/*==============================================================*/
/* Table: osms_cart_product                                     */
/*==============================================================*/
create table osms_cart_product
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   specification_id     bigint,
   quantity             int,
   primary key (id)
);

/*==============================================================*/
/* Table: osms_category                                         */
/*==============================================================*/
create table osms_category
(
   id                   bigint not null auto_increment,
   name                 varchar(64),
   primary key (id)
);

alter table osms_category comment '商品类别表';

/*==============================================================*/
/* Table: osms_comments                                         */
/*==============================================================*/
create table osms_comments
(
   id                   bigint not null,
   user_id              bigint,
   product_id           bigint,
   specification_id     bigint,
   order_id             bigint,
   content              varchar(600),
   score                int,
   createtime           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: osms_dict                                             */
/*==============================================================*/
create table osms_dict
(
   id                   bigint not null auto_increment,
   name                 varchar(100),
   primary key (id)
);

/*==============================================================*/
/* Table: osms_favorite                                         */
/*==============================================================*/
create table osms_favorite
(
   id                   bigint not null auto_increment,
   user_id              bigint not null,
   product_id           bigint not null,
   primary key (id)
);

alter table osms_favorite comment '用户商品收藏';

/*==============================================================*/
/* Table: osms_image                                            */
/*==============================================================*/
create table osms_image
(
   id                   bigint not null auto_increment,
   product_id           bigint,
   url                  varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: osms_message                                          */
/*==============================================================*/
create table osms_message
(
   id                   bigint not null,
   user_id              bigint,
   product_id           bigint,
   specification_id     bigint,
   order_id             bigint,
   content              varchar(600),
   score                int,
   createtime           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: osms_order                                            */
/*==============================================================*/
create table osms_order
(
   id                   bigint not null,
   address_id           bigint,
   specification_id     bigint,
   user_id              bigint,
   status               bigint,
   quantity             int,
   preferentialprice    decimal(10,2),
   totalprice           decimal(10,2),
   createtime           datetime,
   primary key (id)
);

alter table osms_order comment '订单明细表';

/*==============================================================*/
/* Table: osms_payinfo                                          */
/*==============================================================*/
create table osms_payinfo
(
   id                   bigint not null,
   order_id             bigint,
   status               bigint,
   postage              decimal(10,2),
   payment              decimal(10,2),
   platform             int,
   paymenttype          bigint,
   paymenttime          datetime,
   primary key (id)
);

alter table osms_payinfo comment '支付信息表';

/*==============================================================*/
/* Table: osms_preferentiallevel                                */
/*==============================================================*/
create table osms_preferentiallevel
(
   id                   bigint not null auto_increment,
   level                int,
   primary key (id)
);

alter table osms_preferentiallevel comment '优惠等级表';

/*==============================================================*/
/* Table: osms_product                                          */
/*==============================================================*/
create table osms_product
(
   id                   bigint not null auto_increment,
   seller_id            bigint,
   brand_id             bigint,
   category_id          bigint,
   name                 varchar(100),
   describ              varchar(255),
   status               bigint,
   createtime           datetime,
   updatetime           datetime,
   primary key (id)
);

alter table osms_product comment '商品信息表';

/*==============================================================*/
/* Table: osms_replies                                          */
/*==============================================================*/
create table osms_replies
(
   id                   bigint not null,
   seller_id            bigint,
   messages_id          bigint,
   content              varchar(600),
   createtime           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: osms_seller                                           */
/*==============================================================*/
create table osms_seller
(
   id                   bigint not null auto_increment,
   name                 varchar(30),
   location             varchar(255),
   onlinetime           datetime,
   primary key (id)
);

alter table osms_seller comment '商家表';

/*==============================================================*/
/* Table: osms_specification                                    */
/*==============================================================*/
create table osms_specification
(
   id                   bigint not null auto_increment,
   product_id           bigint,
   size                 varchar(64),
   price                decimal(10,2),
   stock                int,
   primary key (id)
);

alter table osms_specification comment '商品规格';

/*==============================================================*/
/* Table: osms_user                                             */
/*==============================================================*/
create table osms_user
(
   id                   bigint not null auto_increment,
   username             varchar(30),
   password             varchar(30),
   sex                  bool,
   phone                varchar(15),
   email                varchar(30),
   headimage            varchar(255),
   role                 bigint,
   score                bigint,
   level                bigint,
   createtime           datetime,
   updatetime           datetime,
   primary key (id)
);

alter table osms_user comment '用户信息表';

alter table osms_activity add constraint FK_osms_activity_level2 foreign key (preferentiallevel_id)
      references osms_preferentiallevel (id) on delete restrict on update restrict;

alter table osms_activity add constraint FK_osms_activity_product foreign key (product_id)
      references osms_product (id) on delete restrict on update restrict;

alter table osms_address add constraint FK_osms_user_address foreign key (user_id)
      references osms_user (id) on delete restrict on update restrict;

alter table osms_cart_product add constraint FK_Reference_17 foreign key (user_id)
      references osms_user (id) on delete restrict on update restrict;

alter table osms_cart_product add constraint FK_osms_cart_product2 foreign key (specification_id)
      references osms_specification (id) on delete restrict on update restrict;

alter table osms_comments add constraint FK_Reference_19 foreign key (id)
      references osms_order (id) on delete restrict on update restrict;

alter table osms_favorite add constraint FK_osms_favorite foreign key (user_id)
      references osms_user (id) on delete restrict on update restrict;

alter table osms_favorite add constraint FK_osms_favorite2 foreign key (product_id)
      references osms_product (id) on delete restrict on update restrict;

alter table osms_image add constraint FK_osms_product_img foreign key (product_id)
      references osms_product (id) on delete restrict on update restrict;

alter table osms_message add constraint FK_Reference_20 foreign key (id)
      references osms_user (id) on delete restrict on update restrict;

alter table osms_order add constraint FK_Reference_14 foreign key (address_id)
      references osms_address (id) on delete restrict on update restrict;

alter table osms_order add constraint FK_Reference_15 foreign key (user_id)
      references osms_user (id) on delete restrict on update restrict;

alter table osms_order add constraint FK_Reference_16 foreign key (specification_id)
      references osms_specification (id) on delete restrict on update restrict;

alter table osms_payinfo add constraint FK_Reference_18 foreign key (order_id)
      references osms_order (id) on delete restrict on update restrict;

alter table osms_product add constraint FK_osms_product_brand foreign key (brand_id)
      references osms_brand (id) on delete restrict on update restrict;

alter table osms_product add constraint FK_osms_product_category foreign key (category_id)
      references osms_category (id) on delete restrict on update restrict;

alter table osms_product add constraint FK_osms_seller_product foreign key (seller_id)
      references osms_seller (id) on delete restrict on update restrict;

alter table osms_replies add constraint FK_Reference_21 foreign key (id)
      references osms_seller (id) on delete restrict on update restrict;

alter table osms_specification add constraint FK_osms_product_specification foreign key (product_id)
      references osms_product (id) on delete restrict on update restrict;

