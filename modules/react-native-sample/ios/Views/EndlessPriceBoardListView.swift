
@objc(EndlessPriceBoardListView)
final class EndlessPriceBoardListView: UIView {
    
    private let ITEM_WIDTH                          = 150.0
    private let ITEM_HEIGHT                         = 100.0
    private let minimumToLoop                       = 8
    private var bufferSize                          = 5
    private var data: [EndlessQuote]                = []
    private var timer: Timer?                       = nil
    var collectionView: UICollectionView!
    @objc var onPressHandle: RCTDirectEventBlock?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setup()
    }
    
    override func willMove(toSuperview newSuperview: UIView?) {
        if newSuperview == nil {
            stopTimer()
        }
    }
}


extension EndlessPriceBoardListView {
    func setData(data: [EndlessQuote]){
        self.data = data
        collectionView.reloadData()
        if data.count >= minimumToLoop {
            startTimer()
        } else {
            stopTimer()
        }
    }
    
    func updateCell(cell: EndlessCollectionViewCell, item: EndlessQuote, needAnimate: Bool) {
        if needAnimate {
            cell.animatePrice(item: item)
        } else {
            cell.updateItemView(item: item)
        }
    }
    
    func updateItem(item: EndlessQuote) {
        if let index = data.firstIndex(where: {$0.key == item.key }) {
            let needAnimate     = self.data[index].tradePrice != item.tradePrice
            self.data[index]    = item
            
            if let cell      = collectionView.cellForItem(at: IndexPath(item: index, section: 0)) as? EndlessCollectionViewCell  {
                updateCell(cell: cell, item: item, needAnimate: needAnimate)
            }
            if index < bufferSize {
                guard let cell      = collectionView.cellForItem(at: IndexPath(item: data.count + index , section: 0)) as? EndlessCollectionViewCell else {
                    return
                }
                updateCell(cell: cell, item: item, needAnimate: needAnimate)
            }
        }
    }
}

extension EndlessPriceBoardListView {
    
    @objc private func autoScroll() {r
        collectionView.contentOffset.x += 1
    }
    
    private func startTimer() {
        if timer == nil {
            timer = Timer(timeInterval: 0.03, target: self, selector: #selector(self.autoScroll), userInfo: nil, repeats: true)
            RunLoop.main.add(timer!, forMode: RunLoop.Mode.common)
        }
    }
    
    private func stopTimer() {
        if timer != nil {
            timer?.invalidate()
            timer = nil
        }
    }
    
    private func setup() {
        let flowLayout                                              = UICollectionViewFlowLayout()
        flowLayout.scrollDirection                                  = .horizontal
        flowLayout.minimumInteritemSpacing                          = 0
        flowLayout.minimumLineSpacing                               = 0
        
        collectionView                                              = UICollectionView(frame: bounds, collectionViewLayout: flowLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints    = false
        
        backgroundColor                                             = .clear
        collectionView.backgroundColor                              = .clear
        collectionView.dataSource                                   = self
        collectionView.delegate                                     = self
        collectionView.register(EndlessCollectionViewCell.uiNib, forCellWithReuseIdentifier: EndlessCollectionViewCell.identifier)
        
        collectionView.showsHorizontalScrollIndicator               = false
        addSubview(collectionView)
        
        collectionView.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: 0).isActive  = true
        collectionView.topAnchor.constraint(equalTo: self.topAnchor, constant: 0).isActive          = true
        collectionView.rightAnchor.constraint(equalTo: self.rightAnchor, constant: 0).isActive      = true
        collectionView.heightAnchor.constraint(equalToConstant: ITEM_HEIGHT).isActive                        = true
    }
}

extension EndlessPriceBoardListView: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: ITEM_WIDTH, height: 100)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if (data.count < minimumToLoop) {
            return data.count
        }
        return data.count + bufferSize
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: EndlessCollectionViewCell.identifier, for: indexPath) as? EndlessCollectionViewCell else {
            return .init()
        }
        cell.delegate   = self

        if data.count < minimumToLoop {
            cell.updateItemView(item: data[indexPath.row])
            return cell
        }
        let currentCell = indexPath.row % data.count
        cell.updateItemView(item: data[currentCell])
        return cell
    }
}

extension EndlessPriceBoardListView: EndlessCollectionViewCellDelegate {
    func handlePressed(keyQuote: String) {
        let item = self.data.first(where: { $0.key == keyQuote })
        if let item = item, let onPressHandle = onPressHandle {
            onPressHandle(["item": item.toDic()])
        }
    }
}

extension EndlessPriceBoardListView: UIScrollViewDelegate {
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == collectionView {
            if data.count < minimumToLoop {
                return
            }
            
            if scrollView.contentOffset.x > ITEM_WIDTH * CGFloat(data.count) {
                collectionView.contentOffset.x -= ITEM_WIDTH * CGFloat(data.count)
            }
            
            if scrollView.contentOffset.x < 0  {
                collectionView.contentOffset.x += ITEM_WIDTH * CGFloat(data.count)
            }
        }
    }
}
