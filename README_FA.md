# راهنمای Build پروژه Foodgo در GitHub

این پروژه Native Android با Kotlin و Jetpack Compose است و برای ساخت خودکار
APK دیباگ در GitHub Actions آماده شده است.

## ساخت و دانلود APK

1. محتوای پوشه پروژه را در ریشه یک Repository گیت‌هاب قرار دهید.
2. وارد تب **Actions** شوید.
3. Workflow با نام **Build Android APK** را باز کنید.
4. روی **Run workflow** و سپس دکمه سبز اجرا بزنید.
5. پس از سبز شدن Build، وارد همان اجرا شوید.
6. از بخش **Artifacts** فایل **foodgo-debug-apk** را دانلود کنید.
7. ZIP دانلودشده را Extract کنید؛ فایل اصلی **Foodgo-debug.apk** است.

Workflow از این تنظیمات استفاده می‌کند:

```text
Runner: Ubuntu 24.04
Java: 21
Gradle: 9.3.1
Android Gradle Plugin: 9.1.1
Task: :app:assembleDebug
GitHub cache: disabled
```

Build دیباگ به keystore اختصاصی، Firebase، فایل `google-services.json`، کلید API
یا فایل `.env` نیاز ندارد.

مسیر خروجی خام Gradle:

```text
app/build/outputs/apk/debug/app-debug.apk
```
