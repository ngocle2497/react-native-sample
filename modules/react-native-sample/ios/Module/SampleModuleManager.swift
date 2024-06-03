import AVFoundation
import Foundation

@objc(SampleModuleManager)
class SampleModuleManager: RCTEventEmitter {
    override class func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    override var methodQueue: dispatch_queue_t! {
        return .main
    }
    
    
    // MARK: - React Function
    @objc
    func setDataList(_ node: NSNumber, type: NSString, data: NSArray) {
        switch String(type) {
        case ListType.ENDLESS:
            let data = EndlessQuote.arrayDicToArrayEndlessQuote(arr: data.compactMap({ $0 as? NSDictionary}))
            let view = findView(withTag: node) as? EndlessPriceBoardListView
            view?.setData(data: data)
            break
        case ListType.PRICE_BOARD:
            let data = Quote.arrayDicToArrayQuote(arr: data.compactMap({ $0 as? NSDictionary}))
            let view = findView(withTag: node) as? PriceBoardListView
            view?.setData(data: data)
            break
        default:
            break
        }
    }
    
    @objc
    func setTheme(_ data: NSDictionary , resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        
        if let colors = data["colors"] as? NSDictionary {
            ThemeManager.updateColorDic(dic: colors)
        }
        if let textStyle = data["textPresets"] as? NSDictionary {
            ThemeManager.updateFontDic(dic: textStyle)
        }
        if let images = data["images"] as? NSDictionary {
            ThemeManager.updateImagesDic(dic: images) {
                resolve(true)
            }
        } else {
            resolve(true)
        }
    }
    
    @objc
    func updateItemList(_ node: NSNumber, type: NSString, data: NSDictionary) {
        switch String(type) {
        case ListType.ENDLESS:
            let view = findView(withTag: node) as? EndlessPriceBoardListView
            view?.updateItem(item: EndlessQuote.dic2EndlessQuote(dic: data))
            break
        case ListType.PRICE_BOARD:
            let view = findView(withTag: node) as? PriceBoardListView
            view?.updateItem(item: Quote.dic2Quote(dic: data))
            break
        default:
            break
        }
    }
    
    @objc
    func updateHeaderList(_ node: NSNumber, nodeHeader: NSNumber) {
        let view = findView(withTag: nodeHeader) as? HeaderViewWrapper
        let listView = findView(withTag: node) as? PriceBoardListView
        listView?.updateHeaderFrame(view: view)
    }
    
    @objc
    func updateFooterList(_ node: NSNumber, nodeFooter: NSNumber) {
        let view = findView(withTag: nodeFooter) as? FooterViewWrapper
        let listView = findView(withTag: node) as? PriceBoardListView
        listView?.updateFooterFrame(view: view)
    }
    
    // MARK: - Private
    private func findView(withTag tag: NSNumber) -> Any? {
        return bridge.uiManager.view(forReactTag: tag)
    }
    
    private func downloadLocalImage(uri: String?, group: DispatchGroup, completion: @escaping ((inout [String: UIImage], String) -> Void )-> Void){
        if let url = URL(string: uri ?? "") {
            group.enter()
            URLSession.shared.dataTask(with: url , completionHandler: { data, response, error in
                
                guard let data = data else {
                    group.leave()
                    return
                }
                completion { v, k in
                    v[k] = UIImage(data: data)
                    group.leave()
                }
            }).resume()
        }
        
    }
}
