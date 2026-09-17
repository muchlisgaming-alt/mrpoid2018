# The ARMv7 is significantly faster due to the use of the hardware FPU
# DIUBAH: Menggunakan 4 arsitektur untuk dukungan 32-bit dan 64-bit
APP_ABI := armeabi-v7a arm64-v8a x86 x86_64

# Minimal API 21 (wajib untuk NDK modern)
APP_PLATFORM := android-21

# WAJIB: Standard Library C++ (libc++_shared)
# Tanpa ini, aplikasi akan crash saat runtime karena std::string, std::vector, dll tidak ter-load
APP_STL := c++_shared

# Mode optimasi
APP_OPTIM := release
