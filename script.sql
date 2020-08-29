USE [BookStore]
GO
/****** Object:  Table [dbo].[Book]    Script Date: 8/29/2020 16:38:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Book](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](500) NOT NULL,
	[Image] [nvarchar](500) NULL,
	[Description] [nvarchar](max) NULL,
	[Price] [float] NOT NULL,
	[Author] [nvarchar](200) NULL,
	[CategoryId] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[IsActive] [bit] NOT NULL,
 CONSTRAINT [PK_Book] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 8/29/2020 16:38:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](500) NOT NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 8/29/2020 16:38:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Discount](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[DiscountCode] [varchar](10) NOT NULL,
	[DiscountPercent] [float] NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
	[Username] [varchar](100) NOT NULL,
	[IsUsed] [bit] NOT NULL,
 CONSTRAINT [PK_Discount_1] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 8/29/2020 16:38:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](100) NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
	[PaymentMethodId] [int] NULL,
	[DiscountId] [int] NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 8/29/2020 16:38:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[OrderId] [int] NOT NULL,
	[BookId] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Price] [float] NOT NULL,
 CONSTRAINT [PK_OrderDetails] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PaymentMethod]    Script Date: 8/29/2020 16:38:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PaymentMethod](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_PaymentMethod] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserAccount]    Script Date: 8/29/2020 16:38:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserAccount](
	[Username] [varchar](100) NOT NULL,
	[Password] [nchar](50) NOT NULL,
	[Fullname] [nchar](50) NULL,
	[IsAdmin] [bit] NOT NULL,
 CONSTRAINT [PK_UserAccount] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Book] ON 

INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (1, N'Làm Bạn Với Bầu Trời', NULL, N'Sách hay nhất', 50000, N'Nguyễn Nhật Anhs', 3, 1000, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (2, N'Cảm ơn người lớn', NULL, N'Sách hay bình thường', 60000, N'Nguyễn Nhật ánh', 3, 500, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (3, N'giải thuật và lập trình', NULL, N'Sách về lập trình', 100000, N'Lê Minh Hoàng', 4, 1200, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (5, N'toán nâng cao lớp 9', NULL, N'Những bài toán hóc búa', 120000, N'Hồ Xuân Tâm', 4, 500, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (6, N'toán nâng cao lớp 10', NULL, N'Những bài toán hóc búa', 120000, N'Hồ Xuân Tâm', 4, 500, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (7, N'toán nâng cao lớp 11', NULL, N'Những bài toán hóc búa', 120000, N'Hồ Xuân Tâm', 4, 500, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (8, N'toán nâng cao lớp 12', NULL, N'Những bài toán hóc búa', 120000, N'Hồ Xuân Tâm', 4, 500, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (10, N'Những người khốn khổ', NULL, NULL, 500000, N'Victor Hugo', 2, 1000, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (11, N'Cuốn Theo chiều gió', NULL, NULL, 500000, N'Victor Hugo', 2, 1000, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (12, N'Con chó nhỏ trong giỏ hoa', NULL, NULL, 20000, N'Nguyễn Nhật Anhs', 1, 1000, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (13, N'Cha giàu cha nghèo', NULL, NULL, 20000, N'Nguyễn Thế Cương', 5, 1000, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (14, N'7 cách để thành công', NULL, NULL, 300000, N'Trần Vĩnh Hy', 5, 100, 1)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (15, N'ABC XYZ', NULL, NULL, 300000, N'Trần Vĩnh Hy', 5, 100, 0)
INSERT [dbo].[Book] ([Id], [Name], [Image], [Description], [Price], [Author], [CategoryId], [Quantity], [IsActive]) VALUES (16, N'1 + 1 != 2', NULL, NULL, 300000, N'Trần Vĩnh Hy', 5, 100, 0)
SET IDENTITY_INSERT [dbo].[Book] OFF
SET IDENTITY_INSERT [dbo].[Category] ON 

INSERT [dbo].[Category] ([Id], [Name]) VALUES (1, N'Truyện Ngắn')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (2, N'Tiểu Thuyết')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (3, N'Truyện Dài')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (4, N'giáo dục')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (5, N'self help')
SET IDENTITY_INSERT [dbo].[Category] OFF
SET IDENTITY_INSERT [dbo].[Discount] ON 

INSERT [dbo].[Discount] ([Id], [DiscountCode], [DiscountPercent], [CreatedDate], [Username], [IsUsed]) VALUES (1, N'abc123', 10, CAST(N'2020-01-01T00:00:00.000' AS DateTime), N'user01', 0)
SET IDENTITY_INSERT [dbo].[Discount] OFF
SET IDENTITY_INSERT [dbo].[PaymentMethod] ON 

INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (1, N'VISA')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (2, N'MOMO')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (3, N'PAYPAL')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (4, N'MASTER CARD')
SET IDENTITY_INSERT [dbo].[PaymentMethod] OFF
INSERT [dbo].[UserAccount] ([Username], [Password], [Fullname], [IsAdmin]) VALUES (N'admin', N'admin                                             ', N'David Ho                                          ', 1)
INSERT [dbo].[UserAccount] ([Username], [Password], [Fullname], [IsAdmin]) VALUES (N'user01', N'user01                                            ', N'Nguyen Thi A                                      ', 0)
ALTER TABLE [dbo].[Book]  WITH CHECK ADD  CONSTRAINT [FK_Book_Category] FOREIGN KEY([CategoryId])
REFERENCES [dbo].[Category] ([Id])
GO
ALTER TABLE [dbo].[Book] CHECK CONSTRAINT [FK_Book_Category]
GO
ALTER TABLE [dbo].[Discount]  WITH CHECK ADD  CONSTRAINT [FK_Discount_UserAccount] FOREIGN KEY([Username])
REFERENCES [dbo].[UserAccount] ([Username])
GO
ALTER TABLE [dbo].[Discount] CHECK CONSTRAINT [FK_Discount_UserAccount]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Discount] FOREIGN KEY([DiscountId])
REFERENCES [dbo].[Discount] ([Id])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Discount]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_PaymentMethod] FOREIGN KEY([PaymentMethodId])
REFERENCES [dbo].[PaymentMethod] ([Id])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_PaymentMethod]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_UserAccount] FOREIGN KEY([Username])
REFERENCES [dbo].[UserAccount] ([Username])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_UserAccount]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Book] FOREIGN KEY([BookId])
REFERENCES [dbo].[Book] ([Id])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Book]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Order] FOREIGN KEY([OrderId])
REFERENCES [dbo].[Order] ([Id])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Order]
GO
