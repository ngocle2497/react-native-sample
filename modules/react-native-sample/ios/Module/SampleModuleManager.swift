import Foundation

final class SampleModuleManager: RCTBridgeModule {

    static func requiresMainQueueSetup() -> Bool {
        return true
    }

    override var methodQueue: DispatchQueue {
        return DispatchQueue.main
    }

    // ===== React Method ===== \\

    @objc func setTheme(data: NSDictionary) {
        
    }

    @objc func setDataList(_ node: NSNumber, data: NSArray) {
        
    }

    @objc func updateItemList(_ node: NSNumber, data: NSDictionary) {
        
    }

    // ===== Private Method ===== \\

    private func findView(with tag: NSNumber) -> T? {
        return bridge.uiManager.view(forReactTag: tag) as? T
    }
}