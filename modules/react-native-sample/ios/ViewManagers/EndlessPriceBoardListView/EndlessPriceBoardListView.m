#import <Foundation/Foundation.h>

#import <React/RCTUtils.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_REMAP_MODULE(EndlessPriceBoardListView, EndlessPriceBoardListViewManager, RCTViewManager)
RCT_EXPORT_VIEW_PROPERTY(onPressHandle, RCTDirectEventBlock);

@end

