import { NextResponse } from "next/server";

export function middleware(request) {
  const isAuthenticated = request.cookies.get("Authorization"); // Check authentication

  if (!isAuthenticated) {
    return NextResponse.redirect(new URL("/", request.url));
  }

  return NextResponse.next(); // Allow access
}

export const config = {
  matcher: ["/cart", "/verify"], // Apply middleware to specific routes
};
