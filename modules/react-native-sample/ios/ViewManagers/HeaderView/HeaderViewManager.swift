import Foundation

@objc(HeaderWrapperViewManager)
class HeaderWrapperViewManager: RCTViewManager {
    
    override var methodQueue: dispatch_queue_t! {
        return .main
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    override func view() -> (HeaderViewWrapper) {
        return HeaderViewWrapper()
    }    
}
