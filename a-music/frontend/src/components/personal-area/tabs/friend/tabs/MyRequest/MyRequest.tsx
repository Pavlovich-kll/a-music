import React, { useEffect } from 'react';
import { MyRequestFriend } from './style';
import { IFriend } from '../../../../../../reducers/friends.d';
import { useDispatch, useSelector } from 'react-redux';
import { IStore } from '../../../../../../store';
import { getRequestedFriends } from '../../../../../../actions/friends';
import { FAKE_IMG } from '../../../../../../constants';

const MyRequest = () => {
  const dispatch = useDispatch();
  const { userId, requestedFriends } = useSelector(({ user: { current }, friends: { requestedFriends } }: IStore) => ({
    userId: current.id,
    requestedFriends,
  }));

  useEffect(() => {
    dispatch(getRequestedFriends(userId));
  }, []);

  return (
    <MyRequestFriend>
      {requestedFriends.map(({ avatar, firstName, lastName }: IFriend) => (
        <div className="list_request">
          <div className="private_data">
            <img src={avatar ? avatar : FAKE_IMG} alt="requestToFriend" />
            <div className="name">
              <span>{firstName}</span>
              <span>{lastName}</span>
            </div>
          </div>
          <span className="response">Waiting for response...</span>
        </div>
      ))}
    </MyRequestFriend>
  );
};

export default MyRequest;
