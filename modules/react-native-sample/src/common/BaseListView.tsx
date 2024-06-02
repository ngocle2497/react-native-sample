import {PureComponent, RefObject, createRef} from 'react';
import {findNodeHandle} from 'react-native';
import {setDataList, updateItemList} from '../module/SampleModule';
import {ListViewType} from '../types/ListViewType';

export class BaseListView<T extends object, P> extends PureComponent<P, {}> {
  listHandleRef: RefObject<any>;
  /**
   *
   */
  constructor(props: P) {
    super(props);
    this.listHandleRef = createRef();
  }

  private get listHandle(): number {
    const node = findNodeHandle(this.listHandleRef.current);
    if (node == null || node < 0) {
      throw new Error('Cannot get list view tag!Does it render correctly?');
    }
    return node;
  }

  protected _setData(data: Array<T>, type: ListViewType) {
    setDataList(this.listHandle, type, data);
  }

  protected _updateItem(data: T, type: ListViewType) {
    updateItemList(this.listHandle, type, data);
  }
}

export default BaseListView;
