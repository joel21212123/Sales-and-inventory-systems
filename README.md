# Sales and Inventory Management System — Bodega "M y C"

Desktop application for managing sales, inventory, purchases, and clients 
for a small retail business ("Bodega M y C"), built with Java Swing and 
MySQL following the MVC architecture pattern.

## Features

- **Product management** — full CRUD, low-stock alerts, category tracking
- **Sales (invoicing)** — invoice generation with subtotal, discount, and IGV (tax) calculation
- **Purchases** — supplier purchase tracking with automatic stock updates
- **Inventory movements** — entry/exit/adjustment tracking with full audit trail
- **Clients & Suppliers** — CRUD management for both
- **Reports** — sales by category, sales by user, products sold, purchase history
- **User authentication** — role-based login system

## Tech Stack

- **Language:** Java (Swing for the desktop UI)
- **Database:** MySQL
- **Architecture:** MVC (Model-View-Controller)
- **IDE:** Apache NetBeans
- **JDBC Driver:** MySQL Connector/J

## Database highlights

The database schema goes beyond basic CRUD tables:

- **9 normalized tables** with proper foreign key relationships
- **Triggers** that automatically update product stock on every purchase 
  and sale (`trg_after_detalle_compra`, `trg_after_detalle_factura`)
- **5 SQL views** for pre-built reports (sales by category, sales by user, 
  best-selling products, purchase details, client purchase history)
- **Indexes** on frequently queried columns for performance

The full schema, including sample data, is in [`database/bodega_myc.sql`](./database/bodega_myc.sql).

## Project structure
