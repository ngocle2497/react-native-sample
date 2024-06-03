import UIKit

@objc(PriceBoardListView)
final class PriceBoardListView: UIView {
    
    private var data: [Quote]   = []
    var tableView: UITableView  = UITableView()
    @objc var onPressHandle: RCTDirectEventBlock?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setup()
    }
    
    override func insertReactSubview(_ subview: UIView!, at atIndex: Int) {
        if let header = subview as? HeaderViewWrapper {
            tableView.tableHeaderView = header
        }
        if let footer = subview as? FooterViewWrapper {
            tableView.tableFooterView = footer
        }
    }
}

extension PriceBoardListView {
    private func setup() {
        backgroundColor                         = .clear
        tableView.backgroundColor               = .clear
        tableView.dataSource                    = self
        tableView.delegate                      = self
        tableView.separatorStyle                = .none
        
        tableView.register(PriceBoardTableViewCell.uiNib, forCellReuseIdentifier: PriceBoardTableViewCell.identifier)
    
        tableView.frame                         = bounds
        tableView.autoresizingMask              = [.flexibleWidth, .flexibleHeight]
        tableView.showsVerticalScrollIndicator  = false
        addSubview(tableView)
    }
}


extension PriceBoardListView {
    
    func setData(data: [Quote]){
        self.data           = data
        self.tableView.reloadData()
    }
    
    func updateItem(item: Quote) {
        if let index = data.firstIndex(where: {$0.key == item.key }) {
            let needAnimate     = self.data[index].tradePrice != item.tradePrice
            self.data[index]    = item
            guard let cell      = tableView.cellForRow(at: IndexPath(item: index, section: 0)) as? PriceBoardTableViewCell else {
                return
            }
            if needAnimate {
                cell.animatedUpdateItem(item: item)
            } else {
                cell.updateItemView(item: item)
            }
        }
    }
    
    func updateHeaderFrame(view: UIView?) {
        if let view = view {
            UIView.animate(withDuration: 0.1) {
                self.tableView.tableHeaderView?.frame = view.frame
            }
        } else {
            tableView.tableHeaderView = view
        }
    }
    
    func updateFooterFrame(view: UIView?) {
        if let view = view {
            UIView.animate(withDuration: 0.1) {
                self.tableView.tableFooterView?.frame = view.frame
            }
        } else {
            tableView.tableFooterView   = view
        }
    }
}

extension PriceBoardListView: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let item = data[indexPath.row]
        guard let cell = tableView.dequeueReusableCell(withIdentifier: PriceBoardTableViewCell.identifier, for: indexPath) as? PriceBoardTableViewCell else {
                return .init()
            }
            cell.delegate             = self
            cell.updateItemView(item: item)
            return cell
    }
}

extension PriceBoardListView: PriceBoardTableViewCellDelegate {
    func handlePressed(keyQuote: String) {
        let index                   = self.data.firstIndex{ $0.key == keyQuote} ?? -1
        if index >= 0 {
            if let onPressHandle = onPressHandle {
                onPressHandle(["item": data[index].toDic()])
            }
        }
        
    }
}

