import Foundation

@objc(EndlessPriceBoardListViewManager)
class EndlessPriceBoardListViewManager: RCTViewManager {
    
    override var methodQueue: dispatch_queue_t! {
        return .main
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    override func view() -> (EndlessPriceBoardListView) {
        return EndlessPriceBoardListView()
    }
    
}
