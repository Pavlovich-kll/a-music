import Authenticate from "../Authenticate";
import React from "react";
import Enzyme from "enzyme";
import { shallow, mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { wait } from "@testing-library/react";

Enzyme.configure({ adapter: new Adapter() });

jest.mock("react-router-dom", () => ({
  useHistory: () => ({
    push: jest.fn(),
  }),
}));

describe("Authenticate tests", () => {
  let wrapper;
  let mockHandleClick = jest.fn();

  const props = {
    signIn: mockHandleClick,
    handleSubmit: mockHandleClick,
    handleMouseDownPassword: mockHandleClick,
    handleClickShowPassword: mockHandleClick,
  };

  beforeEach(() => {
    wrapper = mount(<Authenticate {...props} />);
  });

  it("should renders property", () => {
    expect(wrapper).toMatchSnapshot();
  });

  it("should render component with initial state", () => {
    expect(wrapper.find(".MuiButton-label").length).toBe(1);
    expect(
      wrapper.find('[data-testid="submit-btn"]').find("button").length
    ).toBe(1);
    expect(wrapper.find(".MuiSvgIcon-root").length).toBe(1);
    expect(wrapper.find(".flag-dropdown").length).toBe(1);
    expect(wrapper.find("input").length).toBe(2);
    expect(wrapper.find(".phone-error-text").length).toBe(0);
    expect(wrapper.find(".Mui-error").length).toBe(0);
    expect(wrapper.find(".phone-input").instance().value).toEqual("");
    expect(wrapper.find(".MuiOutlinedInput-input").instance().value).toEqual(
      ""
    );
  });

  it("should click button login", async () => {
    wrapper
      .find(".phone-input")
      .simulate("change", {
        target: { name: "username", value: "375293192966" },
      });
    wrapper
      .find('input[name="password"]')
      .simulate("change", {
        target: { name: "password", value: "Cfdsfsd3332" },
      });
    wrapper.find("form").simulate("submit");
    wrapper.update();
    await wait(() => {
      expect(mockHandleClick).toHaveBeenCalledTimes(1);
    });
  });

    it('should update an phone-input when it is changed on valid value', async () => {
        wrapper.find('.phone-input').simulate("change", {target: {name: "username", value: "375293192966"}});
        wrapper.find("form").simulate("submit");
        await wait(() => {
            wrapper.update();
        });
        expect(wrapper.find(".phone-error-text").exists()).toBeFalsy();
    });

  it("should update an password-input when it is changed on valid value", async () => {
    wrapper
      .find('input[name="password"]')
      .simulate("change", {
        target: { name: "password", value: "Cfdsfsd3332" },
      });
    await wait(() => {
      wrapper.update();
    });
    expect(wrapper.find(".MuiFormHelperText-root").exists()).toBeFalsy();
  });

  it("should update an phone-input when it is changed on empty", async () => {
    wrapper
      .find(".phone-input")
      .simulate("change", { target: { name: "username", value: "" } });
    wrapper.find("form").simulate("submit");
    await wait(() => {
      wrapper.update();
    });
    expect(wrapper.find(".phone-error-text").exists()).toBeTruthy();
  });

  it("should update an password-input when it is changed on empty", async () => {
    wrapper
      .find('input[name="password"]')
      .simulate("change", { target: { name: "password", value: "" } });
    wrapper.find("form").simulate("submit");
    await wait(() => {
      wrapper.update();
    });
    expect(wrapper.find(".MuiFormHelperText-root").exists()).toBeTruthy();
  });

  it("author input should return value password", async () => {
    const password = wrapper.find('[name="password"]').find("input");
    password.find('input[name="password"]').simulate("change", {
      target: { name: "password", value: "test" },
    });
    wrapper.update();
    await wait(() => {
      expect(password.instance().value).toEqual("test");
    });
  });

  it("should change phone-input", async () => {
    const username = wrapper.find('[name="username"]').find("input");
    username.simulate("change", {
      target: { name: "username", value: "375293192966" },
    });
    wrapper.update();
    await wait(() => {
      expect(username.instance().value).toEqual("+375 (29) 319 29 66");
    });
  });

  it("should click showPassword", () => {
    wrapper
      .find('[data-testid="showPassword-btn"]')
      .find("button")
      .simulate("click");
    expect(props.handleMouseDownPassword).toHaveBeenCalledTimes(1);
    expect(props.handleClickShowPassword).toHaveBeenCalledTimes(1);
  });
});
