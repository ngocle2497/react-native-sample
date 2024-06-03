import Foundation

@objc(PriceBoardListViewManager)
class PriceBoardListViewManager: RCTViewManager {
    
    override var methodQueue: dispatch_queue_t! {
        return .main
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    override func view() -> (PriceBoardListView) {
        return PriceBoardListView()
    }
}
