#import <Foundation/Foundation.h>
#import <React/RCTEventEmitter.h>
#import <React/RCTUtils.h>


@interface RCT_EXTERN_REMAP_MODULE (SampleModule, SampleModuleManager, RCTEventEmitter)
RCT_EXTERN_METHOD(setDataList
                  : (nonnull NSNumber*)node type
                  : (NSString*)type data
                  : (NSArray*)data);

RCT_EXTERN_METHOD(updateItemList
                  : (nonnull NSNumber*)node type
                  : (NSString*)type data
                  : (NSDictionary*)data);

RCT_EXTERN_METHOD(updateHeaderList
                  : (nonnull NSNumber*)node nodeHeader
                  : (nonnull NSNumber*)nodeHeader);

RCT_EXTERN_METHOD(updateFooterList
                  : (nonnull NSNumber*)node nodeFooter
                  : (nonnull NSNumber*)nodeFooter);

RCT_EXTERN_METHOD(setTheme
                  : (NSDictionary*) sources resolve
                  :(RCTPromiseResolveBlock)resolve reject
                  :(RCTPromiseRejectBlock)reject);
@end
