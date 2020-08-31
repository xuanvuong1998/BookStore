USE [BookStore]
GO
/****** Object:  Table [dbo].[Book]    Script Date: 8/31/2020 19:41:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Book](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](500) NULL,
	[Image] [nvarchar](500) NULL,
	[Description] [nvarchar](max) NULL,
	[Price] [float] NULL,
	[Author] [nvarchar](200) NULL,
	[ImportDate] [date] NULL,
	[CategoryId] [int] NOT NULL,
	[Quantity] [int] NULL,
	[IsActive] [bit] NOT NULL,
	[ISBN] [nvarchar](100) NULL,
	[NumOfPages] [int] NULL,
	[Link] [nvarchar](500) NULL,
 CONSTRAINT [PK_Book] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 8/31/2020 19:41:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](500) NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 8/31/2020 19:41:56 ******/
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
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 8/31/2020 19:41:56 ******/
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
/****** Object:  Table [dbo].[PaymentMethod]    Script Date: 8/31/2020 19:41:56 ******/
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
/****** Object:  Table [dbo].[ShoppingOrder]    Script Date: 8/31/2020 19:41:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ShoppingOrder](
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
/****** Object:  Table [dbo].[UserAccount]    Script Date: 8/31/2020 19:41:56 ******/
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
SET IDENTITY_INSERT [dbo].[Discount] ON 

INSERT [dbo].[Discount] ([Id], [DiscountCode], [DiscountPercent], [CreatedDate], [Username], [IsUsed]) VALUES (1, N'abc123', 10, CAST(N'2020-01-01T00:00:00.000' AS DateTime), N'user01', 1)
INSERT [dbo].[Discount] ([Id], [DiscountCode], [DiscountPercent], [CreatedDate], [Username], [IsUsed]) VALUES (5, N'hi', 0, CAST(N'2020-08-29T23:18:51.137' AS DateTime), N'user01', 0)
INSERT [dbo].[Discount] ([Id], [DiscountCode], [DiscountPercent], [CreatedDate], [Username], [IsUsed]) VALUES (6, N'hi', 50, CAST(N'2020-08-29T23:19:00.183' AS DateTime), N'user01', 0)
SET IDENTITY_INSERT [dbo].[Discount] OFF
SET IDENTITY_INSERT [dbo].[PaymentMethod] ON 

INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (1, N'COD')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (2, N'VISA')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (3, N'PAYPAL')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (4, N'MASTER CARD')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (5, N'MOMO')
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
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Book] FOREIGN KEY([BookId])
REFERENCES [dbo].[Book] ([Id])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Book]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_ShoppingOrder] FOREIGN KEY([OrderId])
REFERENCES [dbo].[ShoppingOrder] ([Id])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_ShoppingOrder]
GO
ALTER TABLE [dbo].[ShoppingOrder]  WITH CHECK ADD  CONSTRAINT [FK_Order_Discount] FOREIGN KEY([DiscountId])
REFERENCES [dbo].[Discount] ([Id])
GO
ALTER TABLE [dbo].[ShoppingOrder] CHECK CONSTRAINT [FK_Order_Discount]
GO
ALTER TABLE [dbo].[ShoppingOrder]  WITH CHECK ADD  CONSTRAINT [FK_Order_PaymentMethod] FOREIGN KEY([PaymentMethodId])
REFERENCES [dbo].[PaymentMethod] ([Id])
GO
ALTER TABLE [dbo].[ShoppingOrder] CHECK CONSTRAINT [FK_Order_PaymentMethod]
GO
ALTER TABLE [dbo].[ShoppingOrder]  WITH CHECK ADD  CONSTRAINT [FK_Order_UserAccount] FOREIGN KEY([Username])
REFERENCES [dbo].[UserAccount] ([Username])
GO
ALTER TABLE [dbo].[ShoppingOrder] CHECK CONSTRAINT [FK_Order_UserAccount]
GO
