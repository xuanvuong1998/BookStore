delete from OrderDetails
go

delete from ShoppingOrder
go

delete from book
go

delete from Category
go

DBCC CHECKIDENT ('Book', RESEED, 1)
DBCC CHECKIDENT ('Category', RESEED, 1)
DBCC CHECKIDENT ('ShoppingOrder', RESEED, 1)
DBCC CHECKIDENT ('OrderDetails', RESEED, 1)



