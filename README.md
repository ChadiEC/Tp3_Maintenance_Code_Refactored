# 🍔 TP3 – Maintenance du Code  
## Refactor du système de commande et d’inventaire McDo

### 👤 Auteur
**Chadi El-Chami**  
Cégep Marie-Victorin – Cours *420-Maintenance (Groupe 01)*  
Date : *3 novembre 2025*

---

## 🎯 Objectif du TP3
L’objectif de ce travail pratique est de **réusiner (refactorer)** le projet **Système de commande et d’inventaire McDo**, fourni initialement en code monolithique.  
Le comportement fonctionnel doit rester **identique**, mais la structure du code doit être :
- plus claire et modulaire,
- orientée objet,
- conforme aux principes **Clean Code** et **SOLID**.

---

## 🧱 Structure du projet
```
Tp3_Maintenance_Code_Refactored/
├── pom.xml
├── README.md
└── src/
├── main/
│ ├── java/
│ │ └── chadi/
│ │ ├── app/
│ │ │ └── McDonaldApp.java
│ │ ├── ui/
│ │ │ └── ConsoleUI.java
│ │ ├── models/
│ │ │ ├── Item.java
│ │ │ └── CartItem.java
│ │ ├── services/
│ │ │ ├── CartService.java
│ │ │ ├── InventoryManager.java
│ │ │ └── OrderService.java
│ │ └── exceptions/
│ │ └── StockException.java
│ └── resources/
└── test/
└── java/

```
---

## ⚙️ Technologies utilisées
- **Java 17+**
- **Maven** (structure standard)
- **IntelliJ IDEA**
- **Git / GitHub** pour le versionnement

---

## 🧩 Principes appliqués

### 🔹 Clean Code
- Noms explicites et cohérents (`cartItem`, `inventoryManager`, etc.)  
- Fonctions courtes et claires  
- Suppression de la duplication (DRY)  
- Responsabilités bien séparées (SRP)

### 🔹 SOLID
| Principe | Application |
|-----------|-------------|
| **S** (Responsabilité unique) | Chaque classe a une seule responsabilité |
| **O** (Ouvert/Fermé) | Nouveau type d’item ajoutable sans modifier `CartService` |
| **L** (Substitution de Liskov) | Sous-classes d’`Item` peuvent être interchangées |
| **I** (Ségrégation d’interfaces) | Interfaces légères et spécifiques |
| **D** (Inversion des dépendances) | La couche UI dépend d’abstractions |

---

## 🧠 Fonctionnalités principales (inchangées)
- Consultation du **menu client**
- Ajout d’un **item ou d’un trio** au panier  
- Validation du **stock** avant achat  
- Génération d’un **reçu** de commande  
- Gestion du **stock employé** (ajouter / retirer / ajouter un produit)

---

## 🧰 Exécution du projet

### ▶️ Méthode 1 — Depuis IntelliJ
1. Ouvrir le projet dans IntelliJ  
2. Clic droit sur `McDonaldApp.java` → **Run 'McDonaldApp.main()'**  
3. L’application démarre dans la console :


=== MCDONALDS ===

Mode Client

Mode Inventaire

Quitter
Choix:


### ▶️ Méthode 2 — Depuis terminal (Maven)
```bash
mvn clean compile exec:java -Dexec.mainClass="chadi.app.McDonaldApp"

🧾 Notes de réusinage

Les classes et méthodes ont été réorganisées pour séparer la logique Client / Employé.

Le code gère désormais les erreurs via exceptions personnalisées (StockException).

Le comportement visible à l’écran reste identique au projet original.

Le projet est testable et extensible, prêt à accueillir de nouvelles fonctionnalités.

🧩 Auteur et remerciements

Projet réalisé par Chadi El-Chami
dans le cadre du cours 420-Maintenance (Cégep Marie-Victorin).
Encadré par Philip M.
Basé sur le code original du projet Système de commande et d’inventaire McDo.

📜 Licence
