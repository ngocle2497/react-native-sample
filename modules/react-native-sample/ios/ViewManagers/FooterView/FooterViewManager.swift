import Foundation

@objc(FooterWrapperViewManager)
class FooterWrapperViewManager: RCTViewManager {
    
    override var methodQueue: dispatch_queue_t! {
        return .main
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    override func view() -> (FooterViewWrapper) {
        return FooterViewWrapper()
    }
}
