USE [master]
GO
/****** Object:  Database [BookStore]    Script Date: 8/29/2020 11:38:36 AM ******/
CREATE DATABASE [BookStore]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BookStore', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\BookStore.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BookStore_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\BookStore_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [BookStore] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BookStore].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BookStore] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BookStore] SET ARITHABORT OFF 
GO
ALTER DATABASE [BookStore] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BookStore] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BookStore] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BookStore] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BookStore] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BookStore] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BookStore] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BookStore] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BookStore] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BookStore] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BookStore] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BookStore] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BookStore] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BookStore] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BookStore] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BookStore] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BookStore] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BookStore] SET RECOVERY FULL 
GO
ALTER DATABASE [BookStore] SET  MULTI_USER 
GO
ALTER DATABASE [BookStore] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BookStore] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BookStore] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BookStore] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BookStore] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'BookStore', N'ON'
GO
ALTER DATABASE [BookStore] SET QUERY_STORE = OFF
GO
USE [BookStore]
GO
/****** Object:  Table [dbo].[Book]    Script Date: 8/29/2020 11:38:36 AM ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 8/29/2020 11:38:36 AM ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 8/29/2020 11:38:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Discount](
	[DiscountCode] [varchar](10) NOT NULL,
	[DiscountPercent] [float] NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
	[Username] [varchar](100) NOT NULL,
	[IsUsed] [bit] NOT NULL,
 CONSTRAINT [PK_Discount] PRIMARY KEY CLUSTERED 
(
	[DiscountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 8/29/2020 11:38:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](100) NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
	[PaymentMethodId] [int] NULL,
	[DiscountCode] [varchar](10) NOT NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 8/29/2020 11:38:36 AM ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PaymentMethod]    Script Date: 8/29/2020 11:38:36 AM ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserAccount]    Script Date: 8/29/2020 11:38:36 AM ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
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
GO
SET IDENTITY_INSERT [dbo].[Category] ON 

INSERT [dbo].[Category] ([Id], [Name]) VALUES (1, N'Truyện Ngắn')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (2, N'Tiểu Thuyết')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (3, N'Truyện Dài')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (4, N'giáo dục')
INSERT [dbo].[Category] ([Id], [Name]) VALUES (5, N'self help')
SET IDENTITY_INSERT [dbo].[Category] OFF
GO
INSERT [dbo].[Discount] ([DiscountCode], [DiscountPercent], [CreatedDate], [Username], [IsUsed]) VALUES (N'abc123', 10, CAST(N'2020-01-01T00:00:00.000' AS DateTime), N'user01', 0)
GO
SET IDENTITY_INSERT [dbo].[PaymentMethod] ON 

INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (1, N'VISA')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (2, N'MOMO')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (3, N'PAYPAL')
INSERT [dbo].[PaymentMethod] ([Id], [Name]) VALUES (4, N'MASTER CARD')
SET IDENTITY_INSERT [dbo].[PaymentMethod] OFF
GO
INSERT [dbo].[UserAccount] ([Username], [Password], [Fullname], [IsAdmin]) VALUES (N'admin', N'admin                                             ', N'David Ho                                          ', 1)
INSERT [dbo].[UserAccount] ([Username], [Password], [Fullname], [IsAdmin]) VALUES (N'user01', N'user01                                            ', N'Nguyen Thi A                                      ', 0)
GO
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
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Discount] FOREIGN KEY([DiscountCode])
REFERENCES [dbo].[Discount] ([DiscountCode])
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
USE [master]
GO
ALTER DATABASE [BookStore] SET  READ_WRITE 
GO
