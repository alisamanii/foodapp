# آپلود صحیح پروژه در GitHub

## روش مرورگر

1. فایل `foodgo-github-final.zip` را در کامپیوتر Extract کنید.
2. Repository را در GitHub باز کنید.
3. **Add file → Upload files** را انتخاب کنید.
4. وارد پوشه استخراج‌شده شوید و تمام فایل‌ها و پوشه‌های داخل آن را آپلود کنید.
5. مطمئن شوید پوشه مخفی `.github` و فایل `.gitignore` نیز انتخاب شده‌اند.
6. روی **Commit changes** بزنید.
7. تب **Actions** را باز و Workflow **Build Android APK** را اجرا کنید.

خود فایل ZIP را داخل Repository قرار ندهید؛ GitHub آن را برای اجرای پروژه Extract
نمی‌کند.

## ساخت APK

پس از موفق شدن Workflow:

```text
Actions → Build Android APK → Artifacts → foodgo-debug-apk
```

بعد از Extract کردن Artifact، فایل نصب برنامه این است:

```text
Foodgo-debug.apk
```
