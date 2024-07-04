import Button from '@mui/material/Button';

const CustomButton = ({children,onClick=()=>{}},className,style) => {
  return (
    <Button variant="contained" style={{ backgroundColor: '#000000', color: '#ffffff',borderRadius:62,...style }} onClick={onClick} className={className}>
      {children}
    </Button>
  );
};

export default CustomButton;