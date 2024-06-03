import {PureComponent, RefObject, createRef} from 'react';
import {findNodeHandle} from 'react-native';
import {
  setDataList,
  updateFooterList,
  updateHeaderList,
  updateItemList,
} from '../module/SampleModule';
import {ListViewType} from '../types/ListViewType';

export class BaseListView<T extends object, P> extends PureComponent<P, {}> {
  listHandleRef: RefObject<any>;
  headerRef: RefObject<any>;
  footerRef: RefObject<any>;
  /**
   *
   */
  constructor(props: P) {
    super(props);
    this.listHandleRef = createRef();
    this.headerRef = createRef();

    this.footerRef = createRef();
  }

  private get headerHandler(): number {
    // @ts-ignore
    const nodeHandle = findNodeHandle(this.headerRef.current);

    if (nodeHandle == null || nodeHandle < 0) {
      throw new Error('Cannot get header view tag!Does it render correctly?');
    }

    return nodeHandle;
  }

  private get footerHandler(): number {
    // @ts-ignore
    const nodeHandle = findNodeHandle(this.footerRef.current);

    if (nodeHandle == null || nodeHandle < 0) {
      throw new Error('Cannot get footer view tag!Does it render correctly?');
    }

    return nodeHandle;
  }

  private get listHandle(): number {
    const node = findNodeHandle(this.listHandleRef.current);
    if (node == null || node < 0) {
      throw new Error('Cannot get list view tag!Does it render correctly?');
    }
    return node;
  }

  protected onLayoutFooter = () => {
    if (this.footerRef.current) {
      updateFooterList(this.listHandle, this.footerHandler);
    }
  };

  protected onLayoutHeader = () => {
    if (this.headerRef.current) {
      updateHeaderList(this.listHandle, this.headerHandler);
    }
  };

  protected _setData(data: Array<T>, type: ListViewType) {
    setDataList(this.listHandle, type, data);
  }

  protected _updateItem(data: T, type: ListViewType) {
    updateItemList(this.listHandle, type, data);
  }
}

export default BaseListView;
