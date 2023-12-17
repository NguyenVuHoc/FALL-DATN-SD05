create
database fall23_datn_sd05;
use
fall23_datn_sd05;

-- 1.Dòng Sản Phẩm
create table dong_san_pham
(
    id         bigint auto_increment
        primary key,
    ma         varchar(255) not null,
    ngay_sua   date null,
    ngay_tao   date null,
    ten        varchar(255) not null,
    trang_thai tinyint null,
    constraint UK_28w4x0up9146ir61vu5nj6gs4
        unique (ma),
    constraint UK_ti2brqn4tp0othp3iwu97jwtp
        unique (ten)
);

-- 2.Thương Hiệu
create table thuong_hieu
(
    id         bigint auto_increment
        primary key,
    ma         varchar(255) not null,
    ngay_sua   date null,
    ngay_tao   date null,
    ten        varchar(255) not null,
    trang_thai tinyint null,
    constraint UK_123sssxf1x7ax6jyo4wdcvk36
        unique (ma),
    constraint UK_53w31rjwmistgvxj1lk2gnus9
        unique (ten)
);

-- 3.Sản Phẩm
create table san_pham
(
    id               bigint auto_increment
        primary key,
    anh_chinh        varchar(255) null,
    ma               varchar(255) not null,
    mo_ta            varchar(255) null,
    ngay_sua         date null,
    ngay_tao         date null,
    ten              varchar(255) null,
    trang_thai       tinyint null,
    id_dong_san_pham bigint null,
    id_thuong_hieu   bigint null,
    constraint UK_131y1wu003wtcyqns9naaabhr
        unique (ma),
    constraint FK5266y3xb83fch2ygdg6wf58qu
        foreign key (id_thuong_hieu) references thuong_hieu (id),
    constraint FKlds313o255x6s8a6cj58lo5hj
        foreign key (id_dong_san_pham) references dong_san_pham (id)
);

-- 4.Ảnh
create table anh
(
    id          bigint auto_increment
        primary key,
    ten         varchar(255) null,
    url         varchar(255) null,
    id_san_pham bigint null,
    constraint FK2dp9xg8gpf8t1jfw4wxb35o7y
        foreign key (id_san_pham) references san_pham (id)
);

-- 5.Cổ Giày
create table co_giay
(
    id  bigint auto_increment
        primary key,
    ma  varchar(255) not null,
    ten varchar(255) not null,
    constraint UK_511l83jif9dfpiiqfkaa4ofyi
        unique (ten),
    constraint UK_bse8cvun3iodgds58ox9h8yhw
        unique (ma)
);

-- 6.Đế Giày
create table de_giay
(
    id  bigint auto_increment
        primary key,
    ma  varchar(255) not null,
    ten varchar(255) not null,
    constraint UK_3r13k0l32dgevyhvhn3rt6pfx
        unique (ma),
    constraint UK_a977rwuc1r779ie7ftap12b3j
        unique (ten)
);

-- 7.Lót Giày
create table lot_giay
(
    id  bigint auto_increment
        primary key,
    ma  varchar(255) not null,
    ten varchar(255) not null,
    constraint UK_4mo9y78nixlvtod4s0c05hya4
        unique (ma),
    constraint UK_72ahn41lcvil3871vvoofyp0e
        unique (ten)
);

-- 8.Kích Thước
create table kich_thuoc
(
    id  bigint auto_increment
        primary key,
    ma  varchar(255) not null,
    ten varchar(255) not null,
    constraint UK_gkywxpxndarpnx0eo0efvw653
        unique (ten),
    constraint UK_qtq2dqyq8ut9lu7xir4c01y0u
        unique (ma)
);
-- 9.Màu Sắc
create table mau_sac
(
    id  bigint auto_increment
        primary key,
    ma  varchar(255) not null,
    ten varchar(255) not null,
    constraint UK_i8g6p3221tdj7b0i007uyx4uo
        unique (ma),
    constraint UK_obo04bgmvelnnhd3k64hgixj4
        unique (ten)
);

-- 10.Khuyến Mại
create table khuyen_mai
(
    id                        bigint auto_increment
        primary key,
    ma                        varchar(255) null,
    mo_ta                     varchar(255) null,
    muc_giam_gia              int null,
    ngay_bat_dau              date null,
    ngay_ket_thuc             date null,
    ngay_sua                  date null,
    ngay_tao                  date null,
    so_luong_san_pham_ap_dung int null,
    so_luong_san_pham_da_ban  int null,
    ten                       varchar(255) null,
    trang_thai                tinyint null,
    constraint UK_oh5xh866pxnmam6yb83nadq8w
        unique (ma)
);

-- 11.Nhân Viên
create table nhan_vien
(
    id         bigint auto_increment
        primary key,
    email      varchar(255) null,
    gioi_tinh  tinyint null,
    ma         varchar(255) null,
    mat_khau   varchar(255) null,
    ngay_sua   date null,
    ngay_tao   date null,
    sdt        varchar(255) null,
    ten        varchar(255) null,
    trang_thai tinyint null,
    constraint UK_9etpn19qmeos5n3dqc87qild3
        unique (ma),
    constraint UK_mafuwxhl2bcv6obb9fkouokec
        unique (sdt)
);

-- 12.Mã Giảm Giá
create table ma_giam_gia
(
    id               bigint auto_increment
        primary key,
    gia_tri_don_hang decimal(65, 2) null,
    ma               varchar(255) null,
    mo_ta            varchar(255) null,
    muc_giam_gia     int null,
    ngay_bat_dau     date null,
    ngay_ket_thuc    date null,
    ngay_sua         date null,
    ngay_tao         date null,
    so_luong         int null,
    ten              varchar(255) null,
    trang_thai       tinyint null,
    muc_giam_toi_da  decimal(65, 2) null,
    constraint UK_acuss6gxp9w56b8rg3mmg3yux
        unique (ma)
);

-- 13.Chi tiết sản phẩm
create table chi_tiet_san_pham
(
    id            bigint auto_increment
        primary key,
    gia_ban       decimal(65, 2) null,
    gia_mac_dinh  decimal(65, 2) null,
    ngay_sua      date null,
    ngay_tao      date null,
    so_luong_ton  int null,
    trang_thai    tinyint null,
    id_co_giay    bigint null,
    id_de_giay    bigint null,
    id_khuyen_mai bigint null,
    id_kich_thuoc bigint null,
    id_lot_giay   bigint null,
    id_mau_sac    bigint null,
    id_san_pham   bigint null,
    constraint FK5hacuc63k9pfld0eomf5vnrlj
        foreign key (id_lot_giay) references lot_giay (id),
    constraint FK8kv978qwxg4a7g87njtdyyy6a
        foreign key (id_co_giay) references co_giay (id),
    constraint FKbfcsbyjh36fo547ik10d8ejc4
        foreign key (id_kich_thuoc) references kich_thuoc (id),
    constraint FKhrc41nqmp3jsh42ikergp7qsd
        foreign key (id_mau_sac) references mau_sac (id),
    constraint FKhry1oewlwwhwhuqhr1tinw6l6
        foreign key (id_san_pham) references san_pham (id),
    constraint FKq2877t55thubstdrd7ujr7hxi
        foreign key (id_khuyen_mai) references khuyen_mai (id),
    constraint FKtj2f11b2f5l0l8rh9wfnyena2
        foreign key (id_de_giay) references de_giay (id)
);

-- 14.Khách hàng
create table khach_hang
(
    id         bigint auto_increment
        primary key,
    email      varchar(255) null,
    gioi_tinh  tinyint null,
    ma         varchar(255) null,
    mat_khau   varchar(255) null,
    ngay_sua   date null,
    ngay_tao   date null,
    sdt        varchar(255) null,
    ten        varchar(255) null,
    trang_thai tinyint null,
    tich_diem  decimal(65, 2) null,
    constraint UK_1lmmis0qdveete6l4prc9xlad
        unique (ma),
    constraint UK_6gn74xxiy11yxkbb2xmldnlld
        unique (sdt)
);

-- 15.Địa Chỉ
create table dia_chi
(
    id             bigint auto_increment
        primary key,
    dia_chi        varchar(255) null,
    ghi_chu        varchar(255) null,
    ngay_sua       date null,
    ngay_tao       date null,
    sdt            varchar(255) null,
    ten            varchar(255) null,
    ten_nguoi_nhan varchar(255) null,
    phuong_xa      varchar(255) null,
    quan_huyen     varchar(255) null,
    thanh_pho      varchar(255) null,
    trang_thai     tinyint null,
    id_khach_hang  bigint null,
    constraint FKrk60uo19d67v1wpbp5a1rmao5
        foreign key (id_khach_hang) references khach_hang (id)
);

-- 16.Giỏ Hàng
create table gio_hang
(
    id            bigint auto_increment
        primary key,
    ma            varchar(255) null,
    ngay_sua      date null,
    ngay_tao      date null,
    trang_thai    tinyint null,
    id_khach_hang bigint null,
    constraint UK_q7fhxt1ya2dvmjcrrkxisvpl5
        unique (id_khach_hang),
    constraint UK_s20okvj8cy9ux6taewckmju9
        unique (ma),
    constraint FK6c8iirgeg8qcwpq1oa9noxshw
        foreign key (id_khach_hang) references khach_hang (id)
);

-- 17.Hóa Đơn
create table hoa_don
(
    id              bigint auto_increment
        primary key,
    dia_chi         varchar(255) null,
    ghi_chu         varchar(255) null,
    loai_hoa_don    tinyint null,
    ma              varchar(255) null,
    ngay_giao       date null,
    ngay_sua        date null,
    ngay_tao        date null,
    ngay_thanh_toan date null,
    phi_van_chuyen  decimal(65, 2) null,
    sdt             varchar(255) null,
    ten_khach_hang  varchar(255) null,
    thanh_toan      decimal(65, 2) null,
    tong_tien       decimal(65, 2) null,
    trang_thai      tinyint null,
    id_khach_hang   bigint null,
    id_nhan_vien    bigint null,
    id_ma_giam_gia  bigint null,
    xu              decimal(65, 2) null,
    tien_giam_gia   decimal(65, 2) null,
    constraint UK_qc99tpq7eqghgm22o8e06gqyf
        unique (ma),
    constraint FK8t4ha4ehtva0djgtpn7ljexce
        foreign key (id_ma_giam_gia) references ma_giam_gia (id),
    constraint FKkuxkrkgq8yftm4d8d7o0w6nbv
        foreign key (id_nhan_vien) references nhan_vien (id),
    constraint FKrygimdf5nr1g2t6u03gvtr1te
        foreign key (id_khach_hang) references khach_hang (id)
);

-- 18.Hóa đơn chi tiết
create table hoa_don_chi_tiet
(
    id                   bigint auto_increment
        primary key,
    de_giay              varchar(255) null,
    gia_ban              decimal(65, 2) null,
    kich_thuoc           varchar(255) null,
    mau_sac              varchar(255) null,
    ngay_sua             date null,
    ngay_tao             date null,
    so_luong             int null,
    ten_san_pham         varchar(255) null,
    thuong_hieu          varchar(255) null,
    trang_thai           tinyint null,
    id_chi_tiet_san_pham bigint null,
    id_hoa_don           bigint null,
    constraint FK4672qsis193xo4polycwrcwb8
        foreign key (id_chi_tiet_san_pham) references chi_tiet_san_pham (id),
    constraint FK5adopt864mjisuy5xmgmy8iun
        foreign key (id_hoa_don) references hoa_don (id)
);

-- 19.Giỏ hàng chi tiết
create table gio_hang_chi_tiet
(
    id                   bigint auto_increment
        primary key,
    don_gia              decimal(65, 2) null,
    ghi_chu              varchar(255) null,
    ngay_sua             date null,
    ngay_tao             date null,
    so_luong             int null,
    trang_thai           tinyint null,
    id_chi_tiet_san_pham bigint null,
    id_gio_hang          bigint null,
    id_hoa_don           bigint null,
    constraint FKhkle2qtnnet5fq60x6tdhekql
        foreign key (id_gio_hang) references gio_hang (id),
    constraint FKlcvoteetgysdpfavfevmyh3en
        foreign key (id_chi_tiet_san_pham) references chi_tiet_san_pham (id),
    constraint FKr3utb9x7j00p1ghfj4mkhjyct
        foreign key (id_hoa_don) references hoa_don (id)
);

-- Insert
-- 1.dòng sản phẩm
INSERT INTO dong_san_pham (ma, ngay_sua, ngay_tao, ten, trang_thai)
VALUES ('DSP001', '2023-12-08', '2023-10-26', 'Giày chạy bộ', 0),
       ('DSP002', '2023-12-08', '2023-10-26', 'Giày đá bóng', 0),
       ('DSP003', '2023-12-08', '2023-10-26', 'Giày leo núi', 0),
       ('DSP004', '2023-12-08', '2023-10-26', 'Giày đi bộ', 0),
       ('DSP005', '2023-12-08', '2023-10-26', 'Giày sneaker', 0),
       ('DSP006', '2023-12-08', '2023-10-26', 'Giày tennis', 0),
       ('DSP007', '2023-12-08', '2023-10-26', 'Giày tập gym', 0),
       ('DSP008', '2023-12-08', '2023-10-26', 'Giày cầu lông', 0),
       ('DSP009', '2023-12-08', '2023-10-26', 'Giày nhảy', 0),
       ('DSP010', '2023-12-08', '2023-10-26', 'Giày chạy địa hình', 0),
       ('DSP011', '2023-12-08', '2023-10-26', 'Giày custom', 0),
       ('DSP012', '2023-12-08', '2023-10-26', 'Giày limited', 0);
select *
from dong_san_pham;

-- 2.thương hiệu
INSERT INTO thuong_hieu (ma, ngay_sua, ngay_tao, ten, trang_thai)
VALUES ('TH001', '2023-12-08', '2023-10-26', 'Nike', 0),
       ('TH002', '2023-12-08', '2023-10-26', 'Adidas', 0),
       ('TH003', '2023-12-08', '2023-10-26', 'Puma', 0),
       ('TH004', '2023-12-08', '2023-10-26', 'New Balance', 0),
       ('TH005', '2023-12-08', '2023-10-26', 'Converse', 0),
       ('TH006', '2023-12-08', '2023-10-26', 'Skechers', 0),
       ('TH007', '2023-12-08', '2023-10-26', 'Reebok', 0),
       ('TH008', '2023-12-08', '2023-10-26', 'Under Armour', 0),
       ('TH009', '2023-12-08', '2023-10-26', 'ON RUNNING', 0),
       ('TH010', '2023-12-08', '2023-10-26', 'Vans', 0);
select *
from thuong_hieu;

-- 3.sản phẩm
INSERT INTO san_pham (anh_chinh, ma, mo_ta, ngay_sua, ngay_tao, ten, trang_thai, id_dong_san_pham, id_thuong_hieu)
VALUES ('Nike Air Force 1.png', 'SP001', 'Thấm hút mồ hôi', '2023-10-26', '2023-10-26', 'Nike Air Force 1', 0, 1, 1),
       ('Nike Dunk Low.png', 'SP002', 'Thoáng khí', '2023-10-26', '2023-10-26', 'Nike Dunk Low', 0, 2, 1),
       ('Adidas Ultraboost.png', 'SP003', 'Chống trơn trượt', '2023-10-26', '2023-10-26', 'Adidas Ultraboost', 0, 3, 2),
       ('Adidas Galaxy.png', 'SP004', 'Thoáng khí', '2023-10-26', '2023-10-26', 'Adidas Galaxy', 0, 4, 2),
       ('Puma Deviate Nitro 2.png', 'SP005', 'Thoáng khí', '2023-10-26', '2023-10-26', 'Puma Deviate Nitro 2', 0, 5, 3),
       ('Puma Velocity Nitro.png', 'SP006', 'Thoáng khí', '2023-10-26', '2023-10-26', 'Puma Velocity Nitro', 0, 6, 3),
       ('New Balance 2002R.png', 'SP007', 'Thoáng khí', '2023-10-26', '2023-10-26', 'New Balance 2002R', 0, 7, 4),
       ('New Balance Fresh Foam.png', 'SP008', 'Thấm hút mồ hôi', '2023-10-26', '2023-10-26', 'New Balance Fresh Foam',
        0, 8, 4),
       ('Converse Jack Purcell.png', 'SP009', 'Thấm hút mồ hôi', '2023-10-26', '2023-10-26', 'Converse Jack Purcell', 0,
        9, 5),
       ('Converse Cons – One Star.png', 'SP010', 'Thấm hút mồ hôi', '2023-10-26', '2023-10-26',
        'Converse Cons – One Star', 0, 10, 5),
       ('Skechers Bounderc.png', 'SP011', 'Thấm hút mồ hôi', '2023-10-26', '2023-10-26', 'Skechers Bounderc', 0, 11, 6),
       ('Skechers Ultra Flex 3.0.png', 'SP012', 'Chống trơn trượt', '2023-10-26', '2023-10-26',
        'Skechers Ultra Flex 3.0', 0, 12, 6),
       ('Reebok Nano Flex TR.png', 'SP013', 'Thoáng khí', '2023-10-26', '2023-10-26', 'Reebok Nano Flex TR', 0, 3, 7),
       ('Reebok Nano X1.png', 'SP014', 'Thấm hút mồ hôi', '2023-10-26', '2023-10-26', 'Reebok Nano X1', 0, 4, 7),
       ('Under Armour UA HOVR Sonic 4.png', 'SP015', 'Ôm chân', '2023-10-26', '2023-10-26',
        'Under Armour UA HOVR Sonic 4', 0, 5, 8),
       ('Under Armour UA Charged Rogue 2.5.png', 'SP016', 'Trọng lượng nhẹ', '2023-10-26', '2023-10-26',
        'Under Armour UA Charged Rogue 2.5', 0, 11, 8),
       ('ON RUNNING CLOUD 5.png', 'SP017', 'Trọng lượng nhẹ', '2023-10-26', '2023-10-26', 'ON RUNNING CLOUD 5', 0, 12,
        9),
       ('ON RUNNING THE ROGER ADVANTAGE.png', 'SP018', 'Trọng lượng nhẹ', '2023-10-26', '2023-10-26',
        'ON RUNNING THE ROGER ADVANTAGE', 0, 8, 9),
       ('Vans Era Comfycush.png', 'SP019', 'Trọng lượng vừa, đầm chân', '2023-10-26', '2023-10-26',
        'Vans Era Comfycush', 0, 11, 10),
       ('Vans Old Skool Classic.png', 'SP020', 'Trọng lượng vừa', '2023-10-26', '2023-10-26', 'Vans Old Skool Classic',
        0, 12, 10);
select *
from san_pham;

-- 4.ảnh
INSERT INTO anh (url, id_san_pham)
VALUES ('Nike Air Force 1(2).png', 1),
       ('Nike Air Force 1(3).png', 1),
       ('Nike Air Force 1(4).png', 1),

       ('Nike Dunk Low(2).png', 2),
       ('Nike Dunk Low(3).png', 2),
       ('Nike Dunk Low(4).png', 2),

       ('Adidas Ultraboost(2).png', 3),
       ('Adidas Ultraboost(3).png', 3),
       ('Adidas Ultraboost(4).png', 3),

       ('Adidas Galaxy(2).png', 4),
       ('Adidas Galaxy(3).png', 4),
       ('Adidas Galaxy(4).png', 4),

       ('Puma Deviate Nitro 2(2).png', 5),
       ('Puma Deviate Nitro 2(3).png', 5),
       ('Puma Deviate Nitro 2(4).png', 5),

       ('Puma Velocity Nitro(2).png', 6),
       ('Puma Velocity Nitro(3).png', 6),
       ('Puma Velocity Nitro(4).png', 6),

       ('New Balance 2002R(2).png', 7),
       ('New Balance 2002R(3).png', 7),
       ('New Balance 2002R(4).png', 7),

       ('New Balance Fresh Foam(2).png', 8),
       ('New Balance Fresh Foam(3).png', 8),
       ('New Balance Fresh Foam(4).png', 8),

       ('Converse Jack Purcell(2).png', 9),
       ('Converse Jack Purcell(3).png', 9),
       ('Converse Jack Purcell(4).png', 9),

       ('Converse Cons – One Star(2).png', 10),
       ('Converse Cons – One Star(3).png', 10),
       ('Converse Cons – One Star(4).png', 10),

       ('Skechers Bounderc(2).png', 11),
       ('Skechers Bounderc(3).png', 11),
       ('Skechers Bounderc(4).png', 11),

       ('Skechers Ultra Flex 3.0(2).png', 12),
       ('Skechers Ultra Flex 3.0(3).png', 12),
       ('Skechers Ultra Flex 3.0(4).png', 12),

       ('Reebok Nano Flex TR(2).png', 13),
       ('Reebok Nano Flex TR(3).png', 13),
       ('Reebok Nano Flex TR(4).png', 13),

       ('Reebok Nano X1(2).png', 14),
       ('Reebok Nano X1(3).png', 14),
       ('Reebok Nano X1(4).png', 14),

       ('Under Armour UA HOVR Sonic 4(2).png', 15),
       ('Under Armour UA HOVR Sonic 4(3).png', 15),
       ('Under Armour UA HOVR Sonic 4(4).png', 15),

       ('Under Armour UA Charged Rogue 2.5(2).png', 16),
       ('Under Armour UA Charged Rogue 2.5(3).png', 16),
       ('Under Armour UA Charged Rogue 2.5(4).png', 16),

       ('ON RUNNING CLOUD 5(2).png', 17),
       ('ON RUNNING CLOUD 5(3).png', 17),
       ('ON RUNNING CLOUD 5(4).png', 17),

       ('ON RUNNING THE ROGER ADVANTAGE(2).png', 18),
       ('ON RUNNING THE ROGER ADVANTAGE(3).png', 18),
       ('ON RUNNING THE ROGER ADVANTAGE(4).png', 18),

       ('Vans Era Comfycush(2).png', 19),
       ('Vans Era Comfycush(3).png', 19),
       ('Vans Era Comfycush(4).png', 19),

       ('Vans Old Skool Classic(2).png', 20),
       ('Vans Old Skool Classic(3).png', 20),
       ('Vans Old Skool Classic(4).png', 20);
select *
from anh;

-- 5.cổ giày
INSERT INTO co_giay (ma, ten)
VALUES ('CG001', 'Cổ thấp'),
       ('CG002', 'Cổ thun thấp'),
       ('CG003', 'Cổ lửng'),
       ('CG004', 'Cổ thun lửng'),
       ('CG005', 'Cổ cao'),
       ('CG006', 'Cổ thun cao');
select *
from co_giay;

-- 6.đế giày
INSERT INTO de_giay (ma, ten)
VALUES ('DG001', 'Đế PU'),
       ('DG002', 'Đế EVA'),
       ('DG003', 'Đế Cao su lưu hóa'),
       ('DG004', 'Đế PVC'),
       ('DG005', 'Đế Commando'),
       ('DG006', 'Đế BPU');
select *
from de_giay;

-- 7.lót giày
INSERT INTO lot_giay (ma, ten)
VALUES ('LG001', 'Lót ngăn mùi'),
       ('LG002', 'Lót thể thao'),
       ('LG003', 'Lót nửa bàn chân'),
       ('LG004', 'Lót thoáng khí'),
       ('LG005', 'Lót hỗ trợ vòm'),
       ('LG006', 'Lót giảm sốc');
select *
from lot_giay;

-- 8.kích thước
INSERT INTO kich_thuoc (ma, ten)
VALUES ('KT001', '35'),
       ('KT002', '36'),
       ('KT003', '37'),
       ('KT004', '38'),
       ('KT005', '39'),
       ('KT006', '40'),
       ('KT007', '41'),
       ('KT008', '42'),
       ('KT009', '43'),
       ('KT0010', '44'),
       ('KT0011', '45');
select *
from kich_thuoc;

-- 9.màu sắc
INSERT INTO mau_sac (ma, ten)
VALUES ('MS001', 'Đỏ'),
       ('MS002', 'Cam'),
       ('MS003', 'Vàng'),
       ('MS004', 'Lục'),
       ('MS005', 'Lam'),
       ('MS006', 'Đen-Trắng'),
       ('MS007', 'Đen'),
       ('MS008', 'Trắng'),
       ('MS009', 'Xám'),
       ('MS0010', 'Mix'),
       ('MS0011', 'Ngọc trai');
select *
from mau_sac;

-- 10.khuyến Mại
INSERT INTO khuyen_mai (ma, ten, mo_ta, muc_giam_gia, ngay_bat_dau, ngay_ket_thuc, ngay_sua, ngay_tao, trang_thai)
VALUES ('KM22344324', 'Hè vui', 'Khuyến mại mùa hè', 15, '2023-07-01', '2023-08-31', '2023-06-28', '2023-06-28', 1),
       ('KM22342523', 'Back-to-school', 'Khuyến mại back-to-school', 10, '2023-09-01', '2023-09-15', '2023-08-28',
        '2023-08-28', 1),
       ('KM22225543', 'Thứ 6 đen tối', 'Khuyến mại black friday', 20, '2023-12-25', '2023-12-30', '2023-10-26',
        '2023-10-26', 1),
       ('KM22344545', 'Giáng sinh an lành', 'Khuyến mại noenl', 25, '2023-12-01', '2023-12-25', '2023-10-26',
        '2023-10-26', 0),
       ('KM22414324', 'Năm mới sum vầy', 'Khuyến mại năm mới', 12, '2024-01-01', '2024-01-15', '2023-10-26',
        '2023-10-26', 1),
       ('KM22879567', 'Lễ tình nhân', 'Khuyến mại Valentine', 18, '2022-02-01', '2022-02-14', '2022-01-30',
        '2022-01-30', 1),
       ('KM21343455', 'Cuối năm từ tốn', 'Khuyến mại cuối năm', 18, '2023-12-22', '2023-12-31', '2023-12-5',
        '2021-12-01', 2),
       ('KM10054634', 'Đại lễ Quốc Khánh', 'Khuyến mại Quốc Khánh', 18, '2024-08-30', '2024-09-2', '2023-10-26',
        '2023-10-26', 1),
       ('KM54667487', 'Đêm trăng rằm', 'Khuyến mại trung thu', 18, '2024-10-01', '2024-10-10', '2023-10-26',
        '2023-10-26', 1),
       ('KM99768534', 'Tết thiếu nhi', 'Khuyến mại 1/6', 18, '2024-05-25', '2024-06-01', '2023-10-26', '2023-10-26', 1),
       ('KM99768535', 'Phụ Nữ Việt Nam', 'Khuyến mại 20/10', 20, '2024-10-15', '2024-10-20', '2023-10-26', '2023-10-26',
        1);
select *
from khuyen_mai;

-- 11.Nhân Viên
INSERT INTO nhan_vien (email, gioi_tinh, ma, mat_khau, ngay_sua, ngay_tao, sdt, ten, trang_thai)
VALUES ('haodqph27423@fpt.edu.vn', 0, 'NV001', '123456', '2023-10-26', '2020-08-15', '0909881795', 'Dương Quang Thiêm',
        0),
       ('hungtvph27428@fpt.edu.vn', 0, 'NV002', '123456', '2023-10-26', '2020-10-15', '0987353521', 'Trần Việt Dũng',
        0),
       ('hocnvph27417@fpt.edu.vn', 0, 'NV003', '123456', '2023-10-26', '2020-07-15', '0997881781', 'Nguyễn Phong Vũ',
        0),
       ('lanntph27594@fpt.edu.vn', 1, 'NV004', '123456', '2023-10-26', '2020-05-15', '0925119494', 'Nguyẽn Lan Anh', 0),
       ('khanhncph27401@fpt.edu.vn', 0, 'NV005', '123456', '2023-10-26', '2020-10-10', '0987122822',
        'Nguyễn Công Hoàng', 0),
       ('tuannvph27467@fpt.edu.vn', 0, 'NV006', '123456', '2023-10-26', '2020-10-15', '0975666989', 'Ngô Văn Minh', 0),
       ('hungnd@gmail.com', 0, 'NV007', '123456', '2023-10-26', '2020-10-13', '0935083508', 'Nguyễn Duy Hùng', 0),
       ('khanhld@gmail.com', 0, 'NV008', '123456', '2023-10-26', '2020-10-15', '0999889889', 'Lê Duy Khanh', 0),
       ('hangnt@gmail.com', 1, 'NV009', '123456', '2023-10-26', '2020-03-11', '0973558671', 'Vũ Huy Toản', 0),
       ('thuynt@gmail.com', 1, 'NV010', '123456', '2023-10-26', '2020-10-15', '0963858759', 'Nguyễn Thị Thùy', 0),
       ('trangnt@gmail.com', 1, 'NV011', '123456', '2023-10-26', '2020-10-15', '0963858751', 'Nguyễn Thùy Trang', 0);
select *
from nhan_vien;

-- 12.Mã giảm giá
INSERT INTO ma_giam_gia (gia_tri_don_hang, ma, ten, mo_ta, muc_giam_gia, muc_giam_toi_da, ngay_bat_dau, ngay_ket_thuc,
                         ngay_sua, ngay_tao, so_luong, trang_thai)
VALUES (100000, 'MGG22134499', 'Voucher 1', 'Khuyến mại mùa hè', 15, 100000, '2023-07-01', '2023-08-31', '2023-06-28',
        '2023-06-28', 20, 1),
       (1100000, 'MGG12365632', 'Voucher 2', 'Nhân dịp back-to-school', 15, 100000, '2023-07-01', '2023-09-01',
        '2023-09-15', '2023-08-28', 20, 1),
       (2000000, 'MGG12352932', 'Voucher 3', 'Nhân dịp black friday', 15, 100000, '2023-12-19', '2023-12-30',
        '2023-10-26', '2023-10-26', 20, 0),
       (1000000, 'MGG00977532', 'Voucher 4', 'Nhân dịp noenl', 15, 100000, '2023-12-01', '2023-12-25', '2023-10-26',
        '2023-10-26', 20, 0),
       (500000, 'MGG03453424', 'Voucher 5', 'Nhân dịp năm mới', 15, 100000, '2024-01-01', '2024-01-15', '2023-10-26',
        '2023-10-26', 20, 1),
       (900000, 'MGG99654534', 'Voucher 6', 'Nhân dịp Valentine', 15, 100000, '2024-02-01', '2024-02-14', '2023-10-26',
        '2023-10-26', 20, 1),
       (999999, 'MGG12445577', 'Voucher 7', 'Nhân dịp cuối năm', 15, 500000, '2023-12-22', '2023-12-31', '2023-10-26',
        '2023-10-26', 20, 2),
       (1500000, 'MGG45766779', 'Voucher 8', 'Nhân dịp Quốc Khánh', 15, 100000, '2024-08-30', '2024-09-2', '2023-10-26',
        '2023-10-26', 20, 1),
       (3000000, 'MGG78434390', 'Voucher 9', 'Khuyến mại trung thu', 15, 100000, '2024-10-01', '2024-10-10',
        '2023-10-26', '2023-10-26', 20, 1),
       (3000000, 'MGG78434391', 'Voucher 10', 'Nhân dịp 1/6', 15, 100000, '2024-10-01', '2024-10-10', '2023-10-26',
        '2023-10-26', 20, 1),
       (1000000, 'MGG78434392', 'Voucher 11', 'Phụ Nữ Việt Nam', 20, 200000, '2024-10-15', '2024-10-20', '2023-10-26',
        '2023-10-26', 20, 1);
select *
from ma_giam_gia;

-- 13.chi tiết sản phẩm
INSERT INTO chi_tiet_san_pham (gia_ban, gia_mac_dinh, ngay_sua, ngay_tao, so_luong_ton, trang_thai, id_co_giay,
                               id_de_giay, id_kich_thuoc, id_lot_giay, id_mau_sac, id_san_pham)
VALUES (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 100, 0, 1, 1, 3, 1, 7, 1),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 150, 0, 1, 1, 3, 2, 8, 2),
       (3200000.00, 3200000.00, '2023-10-26', '2023-10-26', 200, 0, 1, 1, 3, 3, 4, 3),
       (2500000.00, 2500000.00, '2023-10-26', '2023-10-26', 120, 0, 1, 1, 3, 4, 5, 4),
       (1650000.00, 1650000.00, '2023-10-26', '2023-10-26', 180, 0, 1, 1, 3, 5, 7, 5),
       (999000.00, 999000.00, '2023-10-26', '2023-10-26', 190, 0, 1, 1, 3, 6, 7, 6),
       (680000.00, 680000.00, '2023-10-26', '2023-10-26', 110, 0, 1, 3, 3, 5, 9, 7),
       (1800000.00, 1800000.00, '2023-10-26', '2023-10-26', 130, 0, 1, 3, 3, 4, 4, 8),
       (950000.00, 950000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 3, 3, 3, 7, 9),
       (900000.00, 900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 3, 2, 3, 10),
       (2950000.00, 2950000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 4, 3, 1, 7, 11),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 3, 6, 7, 12),
       (1990000.00, 1990000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 3, 1, 7, 13),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 5, 3, 3, 10, 14),
       (1700000.00, 1700000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 3, 5, 4, 15),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 3, 4, 7, 16),
       (2600000.00, 2600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 6, 3, 2, 7, 17),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 3, 6, 11, 18),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 1, 3, 2, 6, 19),
       (2800000.00, 2800000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 1, 3, 4, 1, 20),

       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 100, 0, 1, 1, 7, 1, 7, 1),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 150, 0, 1, 1, 7, 2, 8, 2),
       (3200000.00, 3200000.00, '2023-10-26', '2023-10-26', 200, 0, 1, 1, 7, 3, 4, 3),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 120, 0, 1, 1, 7, 4, 5, 4),
       (2650000.00, 2650000.00, '2023-10-26', '2023-10-26', 180, 0, 1, 1, 7, 5, 7, 5),
       (3100000.00, 3100000.00, '2023-10-26', '2023-10-26', 190, 0, 1, 1, 7, 6, 7, 6),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 110, 0, 1, 3, 7, 5, 9, 7),
       (2800000.00, 2800000.00, '2023-10-26', '2023-10-26', 130, 0, 1, 3, 7, 4, 4, 8),
       (2950000.00, 2950000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 3, 7, 3, 7, 9),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 7, 2, 3, 10),
       (2950000.00, 2950000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 4, 7, 1, 7, 11),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 7, 6, 7, 12),
       (2700000.00, 2700000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 7, 1, 7, 13),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 5, 7, 3, 10, 14),
       (2700000.00, 2700000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 7, 5, 4, 15),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 7, 4, 7, 16),
       (2600000.00, 2600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 6, 7, 2, 7, 17),
       (850000.00, 850000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 7, 6, 11, 18),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 1, 7, 2, 6, 19),
       (2800000.00, 2800000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 1, 7, 4, 1, 20),

       (1800000.00, 1800000.00, '2023-10-26', '2023-10-26', 100, 0, 1, 1, 8, 1, 7, 1),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 150, 0, 1, 1, 8, 2, 8, 2),
       (3200000.00, 3200000.00, '2023-10-26', '2023-10-26', 200, 0, 1, 1, 8, 3, 4, 3),
       (900000.00, 900000.00, '2023-10-26', '2023-10-26', 120, 0, 1, 1, 8, 4, 5, 4),
       (2650000.00, 2650000.00, '2023-10-26', '2023-10-26', 180, 0, 1, 1, 8, 5, 7, 5),
       (3500000.00, 3500000.00, '2023-10-26', '2023-10-26', 190, 0, 1, 1, 8, 6, 7, 6),
       (2680000.00, 2680000.00, '2023-10-26', '2023-10-26', 110, 0, 1, 3, 8, 5, 9, 7),
       (2800000.00, 2800000.00, '2023-10-26', '2023-10-26', 130, 0, 1, 3, 8, 4, 4, 8),
       (2950000.00, 2950000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 3, 8, 3, 7, 9),
       (900000.00, 900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 8, 2, 3, 10),
       (2600000.00, 2600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 4, 8, 1, 7, 11),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 8, 6, 7, 12),
       (2700000.00, 2700000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 8, 1, 7, 13),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 5, 8, 3, 10, 14),
       (2700000.00, 2700000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 8, 5, 4, 15),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 8, 4, 7, 16),
       (2600000.00, 2600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 6, 8, 2, 7, 17),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 8, 6, 11, 18),
       (870000.00, 870000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 1, 8, 2, 6, 19),
       (2800000.00, 2800000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 1, 8, 4, 1, 20),

       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 100, 0, 1, 1, 11, 1, 7, 1),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 150, 0, 1, 1, 11, 2, 8, 2),
       (3200000.00, 3200000.00, '2023-10-26', '2023-10-26', 200, 0, 1, 1, 11, 3, 4, 3),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 120, 0, 1, 1, 11, 4, 5, 4),
       (2650000.00, 2650000.00, '2023-10-26', '2023-10-26', 180, 0, 1, 1, 11, 5, 7, 5),
       (3100000.00, 3100000.00, '2023-10-26', '2023-10-26', 190, 0, 1, 1, 11, 6, 7, 6),
       (2680000.00, 2680000.00, '2023-10-26', '2023-10-26', 110, 0, 1, 3, 11, 5, 9, 7),
       (800000.00, 800000.00, '2023-10-26', '2023-10-26', 130, 0, 1, 3, 11, 4, 4, 8),
       (2950000.00, 2950000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 3, 11, 3, 7, 9),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 11, 2, 3, 10),
       (2600000.00, 2600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 4, 11, 1, 7, 11),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 4, 11, 6, 7, 12),
       (700000.00, 700000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 11, 1, 7, 13),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 5, 11, 3, 10, 14),
       (2700000.00, 2700000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 5, 11, 5, 4, 15),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 11, 4, 7, 16),
       (2600000.00, 2600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 6, 11, 2, 7, 17),
       (2900000.00, 2900000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 6, 11, 6, 11, 18),
       (1600000.00, 1600000.00, '2023-10-26', '2023-10-26', 195, 0, 1, 1, 11, 2, 6, 19),
       (2800000.00, 2800000.00, '2023-10-26', '2023-10-26', 140, 0, 1, 1, 11, 4, 1, 20);
select *
from chi_tiet_san_pham;

-- 14.khách hàng
INSERT INTO khach_hang (email, gioi_tinh, ma, mat_khau, ngay_sua, ngay_tao, sdt, ten, tich_diem, trang_thai)
VALUES ('khachle@gmail.com', 1, 'KH000', '00000000', '2020-10-26', '2020-10-26', '0000000000', 'Khách lẻ', 0, 0),
       ('haodq245@gmail.com', 0, 'KH001', '123456', '2020-10-26', '2020-10-26', '0986557555', 'Dương Quang Hào', 0, 0),
       ('tranviethung271003@gmail.com', 0, 'KH002', '123456', '2020-10-26', '2020-10-26', '0987654321',
        'Trần Viết Hưng', 0, 0),
       ('vuhoc306@gmail.com', 0, 'KH003', '123456', '2020-10-26', '2020-10-26', '0987397153', 'Nguyễn Vũ Học', 0, 0),
       ('lannguyenx6@gmail.com', 1, 'KH004', '123456', '2020-10-26', '2020-10-26', '0938587259', 'Nguyễn Thị Lan', 0,
        0),
       ('nckhanh1607@gmail.com', 0, 'KH005', '123456', '2020-10-26', '2020-10-26', '0963579603', 'Nguyễn Công Khánh', 0,
        0),
       ('tuannv251222@gmail.com', 0, 'KH006', '123456', '2020-10-26', '2020-10-26', '0986155199', 'Ngô Văn Tuấn', 0, 0),
       ('hanglt@gmail.com', 1, 'KH007', '123456', '2020-10-26', '2020-10-26', '0988757759', 'Lê Thúy Hằng', 0, 0),
       ('longnd@gmail.com', 0, 'KH008', '123456', '2020-10-26', '2020-10-26', '0955432221', 'Đào Kim Linh', 0, 0),
       ('ngant@gmail.com', 1, 'KH009', '123456', '2020-10-26', '2020-10-26', '0977888811', 'Nghiêm Thi Nguyệt Nga', 0,
        0),
       ('hoanx@gmail.com', 0, 'KH010', '123456', '2020-10-26', '2020-10-26', '0965452752', 'Nguyễn Xuân Hòa', 0, 0),
       ('quynhtn@gmail.com', 0, 'KH011', '123456', '2020-10-26', '2020-10-26', '0965452753', 'Nguyễn Thúy Quỳnh', 0, 0);
select *
from khach_hang;

-- 15.địa chỉ
INSERT INTO dia_chi (id, dia_chi, ghi_chu, ngay_sua, ngay_tao, phuong_xa, quan_huyen, sdt, ten_nguoi_nhan, thanh_pho,
                     trang_thai, id_khach_hang)
VALUES (1, 'Làng Gia Phúc', 'Giao hàng nhanh', '2020-10-26', '2020-10-26', '1B2714', '3303', '098655755',
        'Dương Quang Hào', '201', 4, 2),
       (2, 'Ngõ 24 Quan Nhân', 'Giao hàng tiết kiệm', '2020-10-26', '2020-10-26', '1A0607', '1485', '0987654321',
        'Trần Viết Hưng', '201', 4, 3),
       (3, 'Làng Vân Lũng', 'Giao hàng nhanh', '2020-10-26', '2020-10-26', '1B2302', '1805', '0987397153',
        'Nguyễn Vũ Học', 201, 5, 4),
       (4, 'Km8, Đ. Võ Văn Kiệt', 'Giao hàng tiết kiệm', '2020-10-26', '2020-10-26', '1B2902', '1581', '0938587259',
        'Nguyễn Thị Lan', '201', 5, 5),
       (5, 'Xóm lò', 'Giao hàng giờ hành chính', '2020-10-26', '2020-10-26', '13002', '3440', '0963579603',
        'Nguyễn Công Khánh', 201, 4, 6),
       (6, 'Thôn An Bình, Quận 6', 'Giao hàng cuối tuần', '2020-10-26', '2020-10-26', '1B2302', '1805', '0986155199',
        'Ngô Văn Tuấn', '201', 5, 7),
       (7, '258 Nguyễn Thị Minh Khai', 'Giao hàng nhanh', '2020-10-26', '2020-10-26', '21407', '1455', '0988757759',
        'Lê Thúy Hằng', '202', 6, 8),
       (8, '369 Bình Phú', 'Giao hàng tiết kiệm', '2020-10-26', '2020-10-26', '20808', '1450', '0955432221',
        'Đào Duy Long', '202', 6, 9),
       (9, '147 Phạm Văn Đồng', 'Giao hàng buổi sáng', '2020-10-26', '2020-10-26', '20907', '1451', '0977888811',
        'Nghiêm Thi Nguyệt Nga', '202', 5, 10),
       (10, '118 Khuyất Duy Tiến', 'Giao hàng buổi chiều', '2020-10-26', '2020-10-26', '1A0607', '1485', '0965452752',
        'Nguyễn Xuân Hòa', '201', 4, 11),
       (11, '118 Khuyất Duy Tiến', 'Giao hàng buổi chiều', '2020-10-26', '2020-10-26', '1A0607', '1485', '0965452752',
        'Nguyễn Thúy Quỳnh', '201', 4, 12);
select *
from dia_chi;

-- 16.giỏ hàng
INSERT INTO gio_hang (ma, ngay_sua, ngay_tao, trang_thai, id_khach_hang)
VALUES ('GH001', '2023-10-26', '2020-10-26', 0, 2),
       ('GH002', '2023-10-26', '2020-10-26', 0, 3),
       ('GH003', '2023-10-26', '2020-10-26', 0, 4),
       ('GH004', '2023-10-26', '2020-10-26', 0, 5),
       ('GH005', '2023-10-26', '2020-10-26', 0, 6),
       ('GH006', '2023-10-26', '2020-10-26', 0, 7),
       ('GH007', '2023-10-26', '2020-10-26', 0, 8),
       ('GH008', '2023-10-26', '2020-10-26', 0, 9),
       ('GH009', '2023-10-26', '2020-10-26', 0, 10),
       ('GH010', '2023-10-26', '2020-10-26', 0, 11),
       ('GH011', '2023-10-26', '2020-10-26', 0, 12);
select *
from gio_hang;

-- 17.Hóa đơn
INSERT INTO hoa_don (dia_chi, ghi_chu, loai_hoa_don, ma, ngay_giao, ngay_sua, ngay_tao, ngay_thanh_toan, phi_van_chuyen,
                     sdt, ten_khach_hang, thanh_toan, tong_tien, trang_thai, xu, id_khach_hang, id_ma_giam_gia,
                     id_nhan_vien, tien_giam_gia)
VALUES ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 1, 'HD0999954234', '2021-10-10',
        '2021-10-10', '2021-10-3', '2021-10-10', 41000, 0986557555, 'Dương Quang Hào', 1641000.00, 1600000, 5, 0, 2, 1,
        1, 0.00),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 1, 'HD0999954235', '2021-12-30',
        '2021-12-30', '2021-12-23', '2021-12-30', 41000, 0986557555, 'Dương Quang Hào', 1641000.00, 1600000, 6, 0, 2, 1,
        2, 0.00),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 1, 'HD0999954236', '2022-7-30', '2022-7-30',
        '2022-7-24', '2022-7-30', 41000, 0986557555, 'Dương Quang Hào', 1641000.00, 1600000, 5, 0, 2, 1, 3, 0),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 1, 'HD0999954237', '2022-10-27',
        '2022-10-27', '2022-10-20', '2022-10-27', 41000, 0986557555, 'Dương Quang Hào', 1641000.00, 1600000, 5, 0, 2, 1,
        4, 0.00),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 1, 'HD0999954238', '2023-5-22', '2023-5-22',
        '2023-5-17', '2023-5-22', 41000, 0986557555, 'Dương Quang Hào', 1641000.00, 1600000, 6, 0, 2, 1, 5, 0.00),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 0, 'HD0999954239', '2021-8-20', '2021-8-20',
        '2021-8-20', '2021-8-20', 0, 0986557555, 'Dương Quang Hào', 1600000, 1600000, 5, 0, 2, 1, 6, 0.00),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 0, 'HD0999954240', '2022-9-25', '2022-9-25',
        '2022-9-25', '2022-9-25', 0, 0986557555, 'Dương Quang Hào', 1600000, 1600000, 5, 0, 2, 1, 7, 0.00),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 0, 'HD0999954241', '2022-1-1', '2022-1-1',
        '2022-1-1', '2022-1-1', 0, 0986557555, 'Dương Quang Hào', 1600000, 1600000, 5, 0, 2, 1, 8, 0),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 0, 'HD0999954242', '2023-10-20',
        '2023-10-20', '2023-10-20', '2023-10-20', 0, 0986557555, 'Dương Quang Hào', 1600000, 1600000, 5, 0, 2, 1, 9,
        0.00),
       ('Làng Gia Phúc, Xã Nguyễn Trãi, Huyện Thường Tín, Hà Nội', 'Không', 0, 'HD0999954243', '2023-11-25',
        '2023-11-25', '2023-11-25', '2023-11-25', 0, 0986557555, 'Dương Quang Hào', 1600000, 1600000, 5, 0, 2, 1, 10,
        0.00),

       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 1, 'HD0999954244', '2021-10-27',
        '2021-10-27', '2021-10-20', '2021-10-27', 41000, 0987654321, 'Trần Viết Hưng', 1641000.00, 1600000, 5, 0, 3, 1,
        11, 0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 1, 'HD0999954245', '2021-12-27',
        '2021-12-27', '2021-12-20', '2021-12-27', 41000, 0987654321, 'Trần Viết Hưng', 1641000.00, 1600000, 5, 0, 3, 1,
        1, 0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 1, 'HD0999954246', '2022-5-20',
        '2022-5-20', '2022-5-15', '2022-5-20', 41000, 0987654321, 'Trần Viết Hưng', 1641000.00, 1600000, 6, 0, 3, 1, 2,
        0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 1, 'HD0999954247', '2023-3-20',
        '2023-3-20', '2023-3-14', '2023-3-20', 41000, 0987654321, 'Trần Viết Hưng', 1641000.00, 1600000, 5, 0, 3, 1, 3,
        0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 1, 'HD0999954248', '2023-8-20',
        '2023-8-20', '2023-8-13', '2023-8-20', 41000, 0987654321, 'Trần Viết Hưng', 1641000.00, 1600000, 5, 0, 3, 1, 4,
        0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 0, 'HD0999954249', '2021-10-1',
        '2021-10-1', '2021-10-1', '2021-10-1', 0, 0987654321, 'Trần Viết Hưng', 1600000.00, 1600000, 5, 0, 3, 1, 5,
        0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 0, 'HD0999954250', '2021-10-27',
        '2021-10-27', '2021-10-27', '2021-10-27', 0, 0987654321, 'Trần Viết Hưng', 1600000.00, 1600000, 5, 0, 3, 1, 6,
        0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 0, 'HD0999954251', '2022-3-20',
        '2022-3-20', '2022-3-20', '2022-3-20', 0, 0987654321, 'Trần Viết Hưng', 1600000.00, 1600000, 5, 0, 3, 1, 7,
        0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 0, 'HD0999954252', '2023-5-20',
        '2023-5-20', '2023-5-20', '2023-5-20', 0, 0987654321, 'Trần Viết Hưng', 1600000.00, 1600000, 5, 0, 3, 1, 8,
        0.00),
       ('Ngõ 24 Quan Nhân, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 'Không', 0, 'HD0999954253', '2023-10-27',
        '2023-10-27', '2023-10-27', '2023-10-27', 0, 0987654321, 'Trần Viết Hưng', 1600000.00, 1600000, 5, 0, 3, 1, 9,
        0.00),

       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 1, 'HD0999954254', '2021-6-20', '2021-6-20',
        '2021-6-15', '2021-6-20', 41000, 0987397153, 'Nguyễn Vũ Học', 1641000.00, 1600000, 6, 0, 4, 1, 10, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 1, 'HD0999954256', '2021-7-20', '2021-7-20',
        '2021-7-13', '2021-7-20', 41000, 0987397153, 'Nguyễn Vũ Học', 1641000.00, 1600000, 6, 0, 4, 1, 11, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 1, 'HD0999954255', '2021-10-20', '2021-10-20',
        '2021-10-13', '2021-10-20', 41000, 0987397153, 'Nguyễn Vũ Học', 1641000.00, 1600000, 5, 0, 4, 1, 1, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 1, 'HD0999954257', '2022-5-20', '2022-5-20',
        '2022-5-15', '2022-5-20', 41000, 0987397153, 'Nguyễn Vũ Học', 1641000.00, 1600000, 5, 0, 4, 1, 2, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 1, 'HD0999954258', '2023-7-30', '2023-10-20',
        '2023-10-15', '2023-10-20', 41000, 0987397153, 'Nguyễn Vũ Học', 1641000.00, 1600000, 5, 0, 4, 1, 3, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 0, 'HD999954259', '2021-5-2', '2021-5-2',
        '2021-5-2', '2021-5-2', 0, 0987397153, 'Nguyễn Vũ Học', 1600000.00, 1600000, 5, 0, 4, 1, 4, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 0, 'HD999954260', '2022-4-30', '2022-4-30',
        '2022-4-30', '2022-4-30', 0, 0987397153, 'Nguyễn Vũ Học', 1600000.00, 1600000, 5, 0, 4, 1, 5, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 0, 'HD999954261', '2022-6-1', '2022-6-1',
        '2022-6-1', '2022-6-1', 0, 0987397153, 'Nguyễn Vũ Học', 1600000.00, 1600000, 5, 0, 4, 1, 6, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 0, 'HD999954262', '2023-7-20', '2023-7-20',
        '2023-7-20', '2023-7-20', 0, 0987397153, 'Nguyễn Vũ Học', 1600000.00, 1600000, 5, 0, 4, 1, 7, 0.00),
       ('Làng Vân Lũng, Xã An Khánh, Huyện Hoài Đức, Hà Nội', 'Không', 0, 'HD999954263', '2023-12-1', '2023-12-1',
        '2023-12-1', '2023-12-1', 0, 0987397153, 'Nguyễn Vũ Học', 1600000.00, 1600000, 5, 0, 4, 1, 8, 0.00),

       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 1, 'HD999954264', '2021-10-20',
        '2021-10-20', '2021-10-14', '2021-10-20', 41000, 0938587259, 'Nguyễn Thị Lan', 1641000.00, 1600000, 5, 0, 5, 1,
        9, 0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 1, 'HD999954265', '2022-3-20',
        '2022-3-20', '2022-3-16', '2022-3-20', 41000, 0938587259, 'Nguyễn Thị Lan', 1641000.00, 1600000, 5, 0, 5, 1, 10,
        0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 1, 'HD999954266', '2022-8-7',
        '2022-8-7', '2022-8-1', '2022-8-7', 41000, 0938587259, 'Nguyễn Thị Lan', 1641000.00, 1600000, 5, 0, 5, 1, 11,
        0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 1, 'HD999954267', '2022-10-20',
        '2022-10-20', '2022-10-15', '2022-10-20', 41000, 0938587259, 'Nguyễn Thị Lan', 1641000.00, 1600000, 6, 0, 5, 1,
        1, 0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 1, 'HD999954268', '2023-5-20',
        '2023-5-20', '2023-5-15', '2023-5-20', 41000, 0938587259, 'Nguyễn Thị Lan', 1641000.00, 1600000, 6, 0, 5, 1, 2,
        0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 0, 'HD999954269', '2022-7-20',
        '2022-7-20', '2022-7-20', '2022-7-20', 0, 0938587259, 'Nguyễn Thị Lan', 1600000.00, 1600000, 5, 0, 5, 1, 3,
        0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 0, 'HD999954270', '2022-9-20',
        '2022-9-20', '2022-9-20', '2022-9-20', 0, 0938587259, 'Nguyễn Thị Lan', 1600000.00, 1600000, 5, 0, 5, 1, 4,
        0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 0, 'HD999954271', '2022-12-20',
        '2022-12-20', '2022-12-20', '2022-12-20', 0, 0938587259, 'Nguyễn Thị Lan', 1600000.00, 1600000, 5, 0, 5, 1, 5,
        0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 0, 'HD999954272', '2023-9-20',
        '2023-9-20', '2023-9-20', '2023-9-20', 0, 0938587259, 'Nguyễn Thị Lan', 1600000.00, 1600000, 5, 0, 5, 1, 6,
        0.00),
       ('Km8, Đ. Võ Văn Kiệt, Thị trấn Quang Minh, Huyện Mê Linh, Hà Nội', 'Không', 0, 'HD999954273', '2023-10-20',
        '2023-10-20', '2023-10-20', '2023-10-20', 0, 0938587259, 'Nguyễn Thị Lan', 1600000.00, 1600000, 5, 0, 5, 1, 7,
        0.00),

       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 1, 'HD999954274', '2021-2-20', '2021-2-20',
        '2021-2-14', '2021-2-20', 41000, 0963579603, 'Nguyễn Công Khánh', 1641000.00, 1600000, 6, 0, 6, 1, 8, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 1, 'HD999954275', '2021-5-20', '2021-5-20',
        '2021-5-13', '2021-5-20', 41000, 0963579603, 'Nguyễn Công Khánh', 1641000.00, 1600000, 5, 0, 6, 1, 9, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 1, 'HD999954276', '2021-8-20', '2021-8-20',
        '2021-8-15', '2021-8-20', 41000, 0963579603, 'Nguyễn Công Khánh', 1641000.00, 1600000, 6, 0, 6, 1, 10, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 1, 'HD999954277', '2022-9-20', '2022-9-20',
        '2022-9-16', '2022-9-20', 41000, 0963579603, 'Nguyễn Công Khánh', 1641000.00, 1600000, 5, 0, 6, 1, 11, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 1, 'HD999954278', '2022-12-20', '2022-12-20',
        '2022-12-15', '2022-12-20', 41000, 0963579603, 'Nguyễn Công Khánh', 1641000.00, 1600000, 6, 0, 6, 1, 1, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 0, 'HD999954279', '2021-7-20', '2021-7-20',
        '2021-7-20', '2021-7-20', 0, 0963579603, 'Nguyễn Công Khánh', 1600000.00, 1600000, 5, 0, 6, 1, 2, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 0, 'HD999954280', '2021-7-20', '2021-7-20',
        '2021-7-20', '2021-7-20', 0, 0963579603, 'Nguyễn Công Khánh', 1600000.00, 1600000, 5, 0, 6, 1, 3, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 0, 'HD999954281', '2022-3-20', '2022-3-20',
        '2022-3-20', '2022-3-20', 0, 0963579603, 'Nguyễn Công Khánh', 1600000.00, 1600000, 5, 0, 6, 1, 4, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 0, 'HD999954282', '2023-5-20', '2023-5-20',
        '2023-5-20', '2023-5-20', 0, 0963579603, 'Nguyễn Công Khánh', 1600000.00, 1600000, 5, 0, 6, 1, 5, 0.00),
       ('Xóm lò, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội', 'Không', 0, 'HD999954283', '2023-10-20', '2023-10-20',
        '2023-10-20', '2023-10-20', 0, 0963579603, 'Nguyễn Công Khánh', 1600000.00, 1600000, 5, 0, 6, 1, 6, 0.00);
select *
from hoa_don;

-- 18.Hóa đơn chi tiết
INSERT INTO hoa_don_chi_tiet (de_giay, gia_ban, kich_thuoc, mau_sac, ngay_sua, ngay_tao, so_luong, ten_san_pham,
                              thuong_hieu, trang_thai, id_chi_tiet_san_pham, id_hoa_don)
VALUES ('Đế Cao Su Lưu Hóa', 1600000.00, '41', 'Xám', '2021-08-20', '2021-08-20', 1, 'New Balance 2002R', 'New Balance',
        5, 27, 6),
       ('Đế PVC', 1600000.00, '41', 'Đen', '2022-09-25', '2022-09-25', 1, 'Skechers Ultra Flex 3.0', 'Skechers', 5, 32,
        7),
       ('Đế Commando', 1600000.00, '41', 'Mix', '2022-01-01', '2022-01-01', 1, 'Reebok Nano X1', 'Reebok', 5, 34, 8),
       ('Đế PU', 1600000.00, '41', 'Đen-Trắng', '2023-10-20', '2023-10-20', 1, 'Vans Era Comfycush', 'Vans', 5, 39, 9),
       ('Đế PU', 1600000.00, '45', 'Đen', '2023-11-25', '2023-11-25', 1, 'Nike Air Force 1', 'Nike', 5, 61, 10),

       ('Đế Commando', 1600000.00, '41', 'Mix', '2021-10-01', '2021-10-01', 1, 'Reebok Nano X1', 'Reebok', 5, 34, 16),
       ('Đế PU', 1600000.00, '37', 'Đen', '2021-10-27', '2021-10-27', 1, 'Nike Air Force 1', 'Nike', 5, 1, 17),
       ('Đế Commando', 1600000.00, '41', 'Mix', '2022-03-20', '2022-03-20', 1, 'Reebok Nano X1', 'Reebok', 5, 34, 18),
       ('Đế PU', 1600000.00, '37', 'Đen-Trắng', '2023-05-20', '2023-05-20', 1, 'Vans Era Comfycush', 'Vans', 5, 19, 19),
       ('Đế Commando', 1600000.00, '37', 'Mix', '2023-10-27', '2023-10-27', 1, 'Reebok Nano X1', 'Reebok', 5, 14, 20),

       ('Đế PU', 1600000.00, '37', 'Đen', '2021-05-02', '2021-05-02', 1, 'Nike Air Force 1', 'Nike', 5, 1, 26),
       ('Đế Cao Su Lưu Hóa', 1600000.00, '41', 'Xám', '2022-04-30', '2022-04-30', 1, 'New Balance 2002R', 'New Balance',
        5, 27, 27),
       ('Đế PVC', 1600000.00, '41', 'Đen', '2022-06-01', '2022-06-01', 1, 'Skechers Ultra Flex 3.0', 'Skechers', 5, 32,
        28),
       ('Đế PU', 1600000.00, '37', 'Đen', '2023-07-20', '2023-07-20', 1, 'Nike Air Force 1', 'Nike', 5, 1, 29),
       ('Đế Cao Su Lưu Hóa', 1600000.00, '41', 'Xám', '2023-12-01', '2023-12-01', 1, 'New Balance 2002R', 'New Balance',
        5, 27, 30),

       ('Đế PU', 1600000.00, '37', 'Đen-Trắng', '2022-07-20', '2022-07-20', 1, 'Vans Era Comfycush', 'Vans', 5, 19, 36),
       ('Đế Commando', 1600000.00, '37', 'Mix', '2022-09-20', '2022-09-20', 1, 'Reebok Nano X1', 'Reebok', 5, 14, 37),
       ('Đế Cao Su Lưu Hóa', 1600000.00, '41', 'Xám', '2022-12-20', '2022-12-20', 1, 'New Balance 2002R', 'New Balance',
        5, 27, 38),
       ('Đế PVC', 1600000.00, '41', 'Đen', '2023-09-20', '2023-09-20', 1, 'Skechers Ultra Flex 3.0', 'Skechers', 5, 32,
        39),
       ('Đế PU', 1600000.00, '37', 'Đen', '2023-10-20', '2023-10-20', 1, 'Nike Air Force 1', 'Nike', 5, 1, 40),

       ('Đế PU', 1600000.00, '45', 'Đen-Trắng', '2021-07-20', '2021-07-20', 1, 'Vans Era Comfycush', 'Vans', 5, 79, 46),
       ('Đế PVC', 1600000.00, '41', 'Đen', '2021-07-20', '2021-07-20', 1, 'Skechers Ultra Flex 3.0', 'Skechers', 5, 32,
        47),
       ('Đế Commando', 1600000.00, '41', 'Mix', '2022-03-20', '2022-03-20', 1, 'Reebok Nano X1', 'Reebok', 5, 34, 48),
       ('Đế PU', 1600000.00, '37', 'Đen', '2023-05-20', '2023-05-20', 1, 'Nike Air Force 1', 'Nike', 5, 1, 49),
       ('Đế PVC', 1600000.00, '37', 'Đen', '2023-10-20', '2023-10-20', 1, 'Skechers Ultra Flex 3.0', 'Skechers', 5, 12,
        50);
select *
from hoa_don_chi_tiet;

-- 19.giỏ hàng chi tiêt
INSERT INTO gio_hang_chi_tiet (don_gia, ghi_chu, ngay_sua, ngay_tao, so_luong, trang_thai, id_chi_tiet_san_pham,
                               id_gio_hang, id_hoa_don)
VALUES (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 1, 1, 1),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 12, 1, 2),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 14, 1, 3),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 19, 1, 4),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 21, 1, 5),

       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 70, 2, 11),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 79, 2, 12),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 14, 2, 13),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 9, 19, 2, 14),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 9, 1, 2, 15),

       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 70, 3, 21),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 14, 3, 22),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 79, 3, 23),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 32, 3, 24),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 9, 34, 3, 25),

       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 9, 12, 4, 31),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 9, 19, 4, 32),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 32, 4, 33),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 27, 4, 34),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 21, 4, 35),

       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 1, 5, 41),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 27, 5, 42),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 19, 5, 43),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 14, 5, 44),
       (1600000.00, 'Không', '2021-10-10', '2021-10-03', 1, 0, 70, 5, 45);
select *
from gio_hang_chi_tiet;