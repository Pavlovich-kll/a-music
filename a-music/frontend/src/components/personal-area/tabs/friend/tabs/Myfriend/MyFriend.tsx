import React, { useEffect } from 'react';
import { MyFriendStyle } from './style';
import { useDispatch, useSelector } from 'react-redux';
import { getAddedFriends, removeFromFriends } from '../../../../../../actions/friends';
import { IStore } from '../../../../../../store';
import { IFriend } from '../../../../../../reducers/friends.d';
import Button from '@material-ui/core/Button';
import { IDType } from '../../../../../../reducers/reducers';
import cryingCat from './crying-cat-face.png';
import { FAKE_IMG } from '../../../../../../constants';

const MyFriend = () => {
  const dispatch = useDispatch();
  const { userId, addedFriends } = useSelector(({ user: { current }, friends: { addedFriends } }: IStore) => ({
    userId: current.id,
    addedFriends,
  }));

  const handleRemoveFriend = (id: IDType) => () => {
    dispatch(removeFromFriends(id));
  };

  useEffect(() => {
    dispatch(getAddedFriends(userId));
  }, []);

  return (
    <MyFriendStyle>
      {addedFriends.length > 0 ? (
        addedFriends.map(({ avatar, firstName, lastName, phone, email, id }: IFriend) => (
          <div className="friend">
            <img src={avatar ? avatar : FAKE_IMG} alt="friend" width="134" height="200" />
            <div className="info">
              <div className="person_data">
                <span className="name">{firstName}</span>
                <span>{lastName}</span>
              </div>
              <span>{phone}</span>
              <span>{email}</span>
              <Button className="friend_delete" onClick={handleRemoveFriend(id)}>
                Delete
              </Button>
            </div>
          </div>
        ))
      ) : (
        <div>
          It seems you have no friends
          <br />
          <img src={cryingCat} alt="crying cat" />
        </div>
      )}
    </MyFriendStyle>
  );
};

export default MyFriend;
