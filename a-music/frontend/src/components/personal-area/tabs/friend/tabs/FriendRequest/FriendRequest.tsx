import React, { useEffect } from 'react';
import { RequestFriendStyles } from './style';
import Button from '@material-ui/core/Button';
import PersonAddIcon from '@material-ui/icons/PersonAdd';
import PersonAddDisabledIcon from '@material-ui/icons/PersonAddDisabled';
import { IDType } from '../../../../../../reducers/reducers';
import { acceptFriend, getAwaitingFriends, rejectFriend } from '../../../../../../actions/friends';
import { useDispatch, useSelector } from 'react-redux';
import { IStore } from '../../../../../../store';
import { IFriend } from '../../../../../../reducers/friends.d';
import { FAKE_IMG } from '../../../../../../constants';

const FriendRequest = () => {
  const dispatch = useDispatch();
  const { userId, awaitingFriends } = useSelector(({ user: { current }, friends: { awaitingFriends } }: IStore) => ({
    userId: current.id,
    awaitingFriends,
  }));

  const handleRejectFriend = (id: IDType) => () => {
    dispatch(rejectFriend(id));
  };

  const handleAcceptFriend = (friend: IFriend) => () => {
    dispatch(acceptFriend(friend));
  };

  useEffect(() => {
    dispatch(getAwaitingFriends(userId));
  }, []);

  return (
    <RequestFriendStyles>
      {awaitingFriends.map((friend: IFriend) => {
        const { avatar, firstName, lastName, id } = friend;
        return (
          <div className="list_request">
            <div className="private_data">
              <img src={avatar ? avatar : FAKE_IMG} alt="requestToFriend" />
              <div className="name">
                <span>{firstName}</span>
                <span>{lastName}</span>
              </div>
            </div>
            <div>
              <Button className="accept" onClick={handleAcceptFriend(friend)}>
                <PersonAddIcon className="icon" /> Accept
              </Button>
              <Button className="reject" onClick={handleRejectFriend(id)}>
                <PersonAddDisabledIcon className="icon" /> Reject
              </Button>
            </div>
          </div>
        );
      })}
    </RequestFriendStyles>
  );
};

export default FriendRequest;
