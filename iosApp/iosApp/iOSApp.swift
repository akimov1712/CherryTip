import SwiftUI

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    init() {
        KoinInitKt.doInitKoin()
    }

    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    var authHolder: RootHolder { appDelegate.authHolder }

    var body: some Scene {
        WindowGroup {
            RootView(authHolder.auth)
                .onChange(of: scenePhase) { newPhase in
                    switch newPhase {
                    case .background: LifecycleRegistryExtKt.stop(authHolder.lifecycle)
                    case .inactive: LifecycleRegistryExtKt.pause(authHolder.lifecycle)
                    case .active: LifecycleRegistryExtKt.resume(authHolder.lifecycle)
                    @unknown default: break
                    }
                }
        }
    }
}


class AuthHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    let auth: AuthComponent

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        auth = AuthComponentImpl(
            componentContext: DefaultComponentContext(lifecycle: lifecycle)
        )

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the auth component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    let authHolder: AuthHolder = AuthHolder()
}

struct AuthView: UIViewControllerRepresentable {
    let auth: AuthComponent

    func makeUIViewController(context: Context) -> UIViewController {
        return AuthViewControllerKt.authViewController(auth: auth)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
