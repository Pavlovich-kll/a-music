import React from 'react';
import Paper from '@material-ui/core/Paper';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import tab from './constant';
import UserInfo from './tabs/UserInfo';
import { TabPanel } from './TabPanel';
import PaymentInfo from './tabs/PaymentInfo/PaymentInfo';
import Setting from './tabs/Setting';
import { Friend } from './tabs/friend/Friend';
import Notifications from './tabs/user-notifications/Notifications';

const menuItems = [tab.USER_INFO_TAB, tab.FRIENDS_TAB, tab.PAYMENT_INFO_TAB, tab.NOTIFICATIONS_TAB, tab.SETTINGS_TAB];

export const Navigation = () => {
  const [value, setValue] = React.useState('User Info');
  const handleChange = (event: React.ChangeEvent<{}>, newValue: string) => {
    setValue(newValue);
  };

  return (
    <Paper>
      <Tabs value={value} onChange={handleChange} indicatorColor="primary" textColor="primary" centered>
        {menuItems.map((item) => (
          <Tab value={item} key={item} label={item} />
        ))}
      </Tabs>

      <TabPanel value={value} index={'User Info'}>
        <UserInfo />
      </TabPanel>
      <TabPanel value={value} index={'Friends'}>
        <Friend />
      </TabPanel>
      <TabPanel value={value} index={'Payment Info'}>
        <PaymentInfo />
      </TabPanel>
      <TabPanel value={value} index={'Settings'}>
        <Setting />
      </TabPanel>
      <TabPanel value={value} index={'Notifications'}>
        <Notifications />
      </TabPanel>
    </Paper>
  );
};
