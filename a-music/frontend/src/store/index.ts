import { combineReducers, createStore, applyMiddleware, compose, Store } from 'redux';
import thunk from 'redux-thunk';

import UserReducer from '../reducers/user';
import ContentReducer from '../reducers/content';
import FriendsReducer from '../reducers/friends';
import { IUserState, IContentState, IFriendsState } from '../reducers/reducers.d';

declare global {
  interface Window {
    __REDUX_DEVTOOLS_EXTENSION_COMPOSE__?: typeof compose;
  }
}

export interface IStore {
  user: IUserState;
  content: IContentState;
  friends: IFriendsState;
}

const composeEnhancers =
  (window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ &&
    // @ts-ignore
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({ trace: true, traceLimit: 25 })) ||
  compose;

const reducers = combineReducers<IStore>({
  user: UserReducer,
  content: ContentReducer,
  friends: FriendsReducer,
});

const ApplicationStore: Store<IStore> = createStore(reducers, composeEnhancers(applyMiddleware(thunk)));

export default ApplicationStore;
