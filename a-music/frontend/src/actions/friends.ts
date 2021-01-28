import { IDType, IFriendsState } from '../reducers/reducers';
import HTTP from '../common/api';
import { FRIENDS_ACTION_TYPE } from '../constants';
import { Dispatch } from 'react';
import { propEq, reject } from 'ramda';
import { toast } from 'react-toastify';
import { IFriend } from '../reducers/friends.d';

const GET_FRIENDS: string = `${process.env.API_URL}user-service/friend/all-added-friends`;
const GET_AWAITING_FRIENDS: string = `${process.env.API_URL}user-service/friend/all-awaiting-friends`;
const GET_REQUESTED_FRIENDS: string = `${process.env.API_URL}user-service/friend/all-requested-friends`;

const ADD_FRIEND: string = `${process.env.API_URL}user-service/friend/add-friend`;
const REJECT_FRIEND: string = `${process.env.API_URL}user-service/friend/reject-friends`;

const DELETE_FRIEND: string = `${process.env.API_URL}user-service/friend/delete-friend`;
const ACCEPT_FRIEND: string = `${process.env.API_URL}user-service/friend/accept-friend`;

export const getAddedFriends = (id: IDType) => async (dispatch: Dispatch<object>) => {
  try {
    const response: any = await HTTP.get(`${GET_FRIENDS}/${id}`);
    dispatch({
      type: FRIENDS_ACTION_TYPE,
      payload: (state: IFriendsState) => ({
        ...state,
        addedFriends: response.content,
      }),
    });
  } catch (e) {
    toast.error("Added friends hasn't been fetched");
  }
};
// Надо реализовать со стороны BE
export const getAwaitingFriends = (id: IDType) => async (dispatch: Dispatch<object>) => {
  try {
    const response: any = await HTTP.get(`${GET_AWAITING_FRIENDS}/${id}`);
    dispatch({
      type: FRIENDS_ACTION_TYPE,
      payload: (state: IFriendsState) => ({
        ...state,
        awaitingFriends: response.content,
      }),
    });
  } catch (e) {
    toast.error("Awaiting friends hasn't been fetched");
  }
};
// Надо реализовать со стороны BE
export const getRequestedFriends = (id: IDType) => async (dispatch: Dispatch<object>) => {
  try {
    const response: any = await HTTP.get(`${GET_REQUESTED_FRIENDS}/${id}`);
    dispatch({
      type: GET_REQUESTED_FRIENDS,
      payload: (state: IFriendsState) => ({
        ...state,
        requestedFriends: response.content,
      }),
    });
  } catch (e) {
    toast.error("Requested friends hasn't been fetched");
  }
};

export const removeFromFriends = (id: IDType) => async (dispatch: Dispatch<object>) => {
  try {
    await HTTP.delete(`${DELETE_FRIEND}/${id}`);
    dispatch({
      type: FRIENDS_ACTION_TYPE,
      payload: (state: IFriendsState) => ({
        ...state,
        addedFriends: reject(propEq('id', id))(state.addedFriends),
      }),
    });
  } catch (e) {
    toast.error("Friend hasn't been deleted");
  }
};

export const rejectFriend = (id: IDType) => async (dispatch: Dispatch<object>) => {
  try {
    await HTTP.post(`${REJECT_FRIEND}/${id}`);
    dispatch({
      type: FRIENDS_ACTION_TYPE,
      payload: (state: IFriendsState) => ({
        ...state,
        awaitingFriends: reject(propEq('id', id))(state.awaitingFriends),
      }),
    });
  } catch (e) {
    toast.error("Friend hasn't been rejected");
  }
};

export const acceptFriend = (friend: IFriend) => async (dispatch: Dispatch<object>) => {
  const { id } = friend;
  try {
    await HTTP.post(`${ACCEPT_FRIEND}/${id}`);
    dispatch({
      type: FRIENDS_ACTION_TYPE,
      payload: (state: IFriendsState) => ({
        ...state,
        addedFriends: [...state.addedFriends, friend],
        awaitingFriends: reject(propEq('id', id))(state.awaitingFriends),
      }),
    });
  } catch (e) {
    toast.error("Friend hasn't been accepted");
  }
};

export const addFriend = (friend: IFriend) => async (dispatch: Dispatch<object>) => {
  const { id } = friend;
  try {
    await HTTP.post(`${ADD_FRIEND}/${id}`);
    dispatch({
      type: FRIENDS_ACTION_TYPE,
      payload: (state: IFriendsState) => ({
        ...state,
        requestedFriends: [...state.addedFriends, friend],
      }),
    });
  } catch (e) {
    toast.error("Friend hasn't been added");
  }
};
