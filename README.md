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
## Project structure

```
src/bodega_myc_/
├── Vista/          → Swing UI forms (views)
├── Ctrl/           → Controllers (business logic, event handling)
├── Modelo/         → Data models (POJOs)
└── ConexionMysql/  → Database connection and query classes (DAO layer)
```
## Getting started

### Prerequisites
- Java JDK 17+ (developed/tested on JDK 21 and 24)
- MySQL Server (or XAMPP with MySQL enabled)
- Apache NetBeans (recommended) or any IDE with Ant support

### Setup

1. Clone the repository
```bash
   git clone https://github.com/joel21212123/Sales-and-inventory-systems.git
```

2. Create the database by running the SQL script in `database/bodega_myc.sql` 
   through phpMyAdmin, MySQL Workbench, or the MySQL CLI. This creates the 
   database, tables, triggers, views, and sample data.

3. Open the project in NetBeans (`File → Open Project`) and select the 
   `bodega_myc_` folder.

4. Check the database connection settings in 
   `src/bodega_myc_/ConexionMysql/ConexionClass.java` and adjust the port/
   credentials if your local MySQL setup differs from the defaults 
   (`localhost:3306`, user `root`, no password).

5. Run the project (`Run → Run Project` or F6).

### Default login credentials (sample data)
| Username | Password | Role |
|---|---|---|
| admin | admin123 | Administrator |
| vendedor1 | vendedor123 | Salesperson |
| almacen | almacen123 | Warehouse |

## Screenshots

_Coming soon_

## Known limitations

- Passwords are currently stored in plain text (planned improvement: bcrypt hashing)
- No connection pooling — each query opens a new connection (fine for this scale, would need HikariCP for production use)
- Database credentials are hardcoded in `ConexionClass.java` (would move to an external config file for production)

## Author

Cristopher Joel Saldaña Peralta — Systems Engineering student, Universidad César Vallejo (Piura, Perú)
