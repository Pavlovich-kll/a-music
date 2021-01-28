import React from 'react';
import { FriendsStyles } from './styles';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import tab from './constant';
import FriendRequest from './tabs/FriendRequest/FriendRequest';
import { TabPanel } from '../../TabPanel';
import MyFriend from './tabs/Myfriend/MyFriend';
import MyRequest from './tabs/MyRequest/MyRequest';

const menuItems = [tab.MY_FRIEND_TAB, tab.FRIEND_REQUEST_TAB, tab.MY_REQUEST_TAB];

export const Friend = () => {
  const [value, setValue] = React.useState('My friend');
  const handleChange = (event: React.ChangeEvent<{}>, newValue: string) => {
    setValue(newValue);
  };

  return (
    <FriendsStyles>
      <Tabs
        orientation="vertical"
        variant="scrollable"
        value={value}
        onChange={handleChange}
        aria-label="Vertical tabs example"
      >
        {menuItems.map((item) => (
          <Tab value={item} key={item} label={item} />
        ))}
      </Tabs>
      <TabPanel value={value} index={tab.MY_FRIEND_TAB}>
        <MyFriend />
      </TabPanel>
      <TabPanel value={value} index={tab.FRIEND_REQUEST_TAB}>
        <FriendRequest />
      </TabPanel>
      <TabPanel value={value} index={tab.MY_REQUEST_TAB}>
        <MyRequest />
      </TabPanel>
    </FriendsStyles>
  );
};
