import React from 'react';
import Input from './Input';

const EmailInput = ({ 
  label = 'Email', 
  name = 'email', 
  value, 
  onChange, 
  error, 
  placeholder = 'example@email.com',
  required = true 
}) => {
  return (
    <Input
      label={label}
      name={name}
      type="email"
      value={value}
      onChange={onChange}
      error={error}
      placeholder={placeholder}
      required={required}
    />
  );
};

export default EmailInput;
