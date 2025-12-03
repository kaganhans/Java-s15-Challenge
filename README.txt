# 📚 Library Management System – Java OOP Project

Bu proje, Java kullanılarak tamamen nesne tabanlı (OOP) prensiplerle geliştirilmiş bir **Kütüphane Otomasyon Sistemidir**.
Konsol tabanlı olup kullanıcı etkileşimi `Scanner` aracılığıyla sağlanmıştır.
Amaç; kitap, üye, ödünç alma, iade ve fatura süreçlerini modelleyen gerçek bir kütüphane senaryosu oluşturmaktır.

---

# 🎯 Projenin Amacı

- Nesne tabanlı yazılım tasarımı (OOD) uygulamak
- Class ilişkilerini doğru şekilde kurmak (inheritance, composition, aggregation)
- List / Set / Map gibi koleksiyon yapılarıyla veri yönetimi yapmak
- Menü tabanlı bir konsol UI tasarlamak
- Ödünç alma–iade–fatura kayıtlarını takip etmek
- Gerçek hayata yakın, tam fonksiyonel bir kütüphane mantığı kurmak

---

# 🧩 OOP İlkelerinin Kullanımı

## ✔️ Encapsulation
Tüm model sınıflarında alanlar `private` olarak tanımlandı ve erişimler getter–setter metodlarıyla sağlandı.
Örnek:
- Book
- Member
- Library
- Invoice

## ✔️ Inheritance (Kalıtım)

Person (abstract)
 ├── Author
 └── Member

## ✔️ Abstract Class
Person sınıfı, tüm kişilerde ortak olan:
- id
- name
- email
- phone

gibi alanları içerir.

## ✔️ Polymorphism
Person türünden Author ve Member nesneleri polymorphic şekilde kullanılabilir.

## ✔️ Composition
Library sınıfı şu bileşenleri **sahiplenir**:
- Map<Long, Book> booksById
- Map<Long, Member> membersById
- List<BorrowRecord> borrowRecords
- List<Invoice> invoices

Member sınıfı:
- Set<Book> borrowedBooks
ile kullanıcının aldığı kitapları tutar.

## ✔️ Collection Framework Kullanımı
- List → BorrowRecord, Invoice
- Set → Member.borrowedBooks
- Map → Library.booksById ve Library.membersById

---

# 🗂 Paket Yapısı

org.example.library
 ├── model
 │     ├── Person
 │     ├── Author
 │     ├── Member
 │     ├── Book
 │     ├── BorrowRecord
 │     ├── Invoice
 │     ├── InvoiceStatus
 │     └── Library
 │
 ├── service
 │     └── LibraryService
 │
 └── ui
       └── LibraryApplication

---

# ⚙️ Proje Özellikleri

## 📚 Kitap İşlemleri
- Ekleme
- Güncelleme
- Silme
- ID ile arama
- İsim ile arama
- Yazara göre arama
- Kategoriye göre listeleme
- Tüm kitapları görüntüleme

## 👤 Üye İşlemleri
- Yeni üye ekleme
- ID ile üye bulma

## 🔄 Ödünç Alma – İade İşlemleri
- Üye kitap alabilir (max limit = 5 kitap)
- Kitap ödünç alındığında:
  - available = false
  - BorrowRecord oluşturulur
  - Invoice (PAID) oluşturulur
- Kitap iade edildiğinde:
  - BorrowRecord güncellenir
  - Kitap yeniden müsait olur
  - Refund Invoice (REFUNDED) oluşturulur

## 💳 Fatura Sistemi
Ödünç alma sırasında:
Invoice{id=1, amount=100.0, status=PAID}

İade sırasında:
Invoice{id=2, amount=-100.0, status=REFUNDED}

---

# 🖥 Konsol Menüsü

1 - Yeni Üye Ekle
2 - Yeni Kitap Ekle
3 - ID ile Kitap Ara
4 - İsim ile Kitap Ara
5 - Yazar ile Kitap Ara
6 - Kategoriye Göre Kitap Listele
7 - Kitap Güncelle
8 - Kitap Sil
9 - Tüm Kitapları Listele
10 - Kitap Ödünç Al
11 - Kitap İade Et
0 - Çıkış

---

# 🧪 Örnek Test Senaryosu

Üye Ekle:
Üye ID: 1
İsim: Kagan
Email: kagan@test.com
Telefon: 5555

Kitap Ekle:
Kitap ID: 100
Ad: Harry Potter
Yazar ID: 10
Yazar: J.K.Rowling
Kategori: Fantasy
Fiyat: 100

Ödünç Alma:
Üye ID: 1
Kitap ID: 100

İade:
Üye ID: 1
Kitap ID: 100

---

# 🏁 Sonuç

Bu proje; tüm gereksinimlerin eksiksiz karşılandığı, OOP ilkelerinin doğru şekilde uygulandığı tam kapsamlı bir Java kütüphane otomasyon sistemidir.
